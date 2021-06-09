package com.unq.edu.tpi.tip.backend.mappers;

import com.unq.edu.tpi.tip.backend.models.Invoice;
import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceMapper {
    public List<InvoiceDTO> mapEntitiesIntoDTOs(Iterable<Invoice> entities) {
        List<InvoiceDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(this.mapEntityIntoDTO(e)));

        return dtos;
    }

    public InvoiceDTO mapEntityIntoDTO(Invoice invoice)
    {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setNumber(invoice.getNumber());
        invoiceDTO.setPrefix(invoice.getPrefix());
        invoiceDTO.setPaymentType(invoice.getPaymentType());

        return invoiceDTO;
    }

}
