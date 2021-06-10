package com.unq.edu.tpi.tip.backend.controllers;

import com.mercadopago.exceptions.MPConfException;
import com.unq.edu.tpi.tip.backend.aspects.ExceptionAspect;
import com.unq.edu.tpi.tip.backend.exceptions.InvoiceDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceDTO;
import com.unq.edu.tpi.tip.backend.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// SDK de Mercado Pago
import com.mercadopago.MercadoPago;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    private final InvoiceService invoiceService;
    // Agrega credenciales

    public InvoiceController(InvoiceService invoiceService) throws MPConfException {
        this.invoiceService = invoiceService;
        MercadoPago.SDK.setAccessToken("PROD_ACCESS_TOKEN");
    }

    @ExceptionAspect
    @PostMapping(path = "/{tableId}", produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createInvoice(@PathVariable("tableId") Long tableId,
            @RequestBody InvoiceDTO invoiceDTO) throws TableDoesNotExistException, InvoiceDoesNotHaveOrdersException
    {
        InvoiceDTO createdInvoice = invoiceService.createInvoice(tableId, invoiceDTO);
        return new ResponseEntity<InvoiceDTO>(createdInvoice, HttpStatus.CREATED);
    }
}
