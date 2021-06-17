package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.aspects.ExceptionAspect;
import com.unq.edu.tpi.tip.backend.exceptions.InvoiceDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceDTO;
import com.unq.edu.tpi.tip.backend.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
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
