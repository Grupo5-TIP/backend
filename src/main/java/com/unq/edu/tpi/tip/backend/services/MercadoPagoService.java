package com.unq.edu.tpi.tip.backend.services;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.unq.edu.tpi.tip.backend.models.dtos.MercadoPagoURLDTO;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class MercadoPagoService {
    public MercadoPagoURLDTO createAndRedirect(Long tableId, Float price) throws MPException, IOException {
        Properties prop = new Properties();

        InputStream input = Test.class.getResourceAsStream("/config.properties");
        prop.load(input);

        MercadoPago.SDK.setAccessToken(prop.getProperty("mercadoPagoAccessToken"));
        Preference preference = new Preference();

        /* TARJETAS PRUEBAS
        Status      Tarjeta	            Número	                Código de seguridad	    Fecha de vencimiento
        Success     Mastercard	        5031 7557 3453 0604	    123	                    11/25
        Failure     Mastercard	        5182 4320 0439 0716	    347	                    04/23
        Success     American Express	3711 803032 57522	    1234	                11/25
         */
        preference.setBackUrls(
                new BackUrls().setFailure("http://localhost:3000/checkout/error/"+tableId)
                    .setPending("http://localhost:3000/checkout/"+tableId)
                    .setSuccess("http://localhost:3000/checkout/success/"+tableId));

        Item item = new Item();
        item.setTitle("Consumo Restaurant")
                .setQuantity(1)
                .setUnitPrice(price);
        preference.appendItem(item);

        String url = preference.save().getSandboxInitPoint();

        MercadoPagoURLDTO mpDTO = new MercadoPagoURLDTO(url);

        return mpDTO;
    }
}
