package com.unq.edu.tpi.tip.backend;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MercadoPagoTests {

    @Test
    public void shouldCreateABasicMercadoPagoCheckout() throws MPException {
        Properties prop = new Properties();
        try {
            InputStream input = Test.class.getResourceAsStream("/config.properties");
            prop.load(input);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        MercadoPago.SDK.setAccessToken(prop.getProperty("mercadoPagoAccessToken"));

        // Crea un objeto de preferencia
        Preference preference = new Preference();

        // Crea un ítem en la preferencia
        Item item = new Item();
        item.setTitle("Mi producto")
                .setQuantity(1)
                .setUnitPrice((float) 75.56);
        preference.appendItem(item);
        preference.save();
        preference.getId();
    }
}

