package com.unq.edu.tpi.tip.backend.controllers;

import com.mercadopago.exceptions.MPException;
import com.unq.edu.tpi.tip.backend.aspects.ExceptionAspect;
import com.unq.edu.tpi.tip.backend.models.dtos.MercadoPagoInvoiceDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.MercadoPagoURLDTO;
import com.unq.edu.tpi.tip.backend.services.MercadoPagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/mp/createlink")
@CrossOrigin(origins = "*")
public class MercadoPagoController {
    private MercadoPagoService mercadoPagoService;

    public MercadoPagoController (MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionAspect
    public ResponseEntity<?> createAndRedirect(@RequestBody MercadoPagoInvoiceDTO mpInvoice)
            throws MPException, IOException {
        MercadoPagoURLDTO mpURLDTO = mercadoPagoService.createAndRedirect(
                mpInvoice.getTableId(),
                mpInvoice.getPrice());

        return new ResponseEntity<>(mpURLDTO, HttpStatus.CREATED);
    }

}
