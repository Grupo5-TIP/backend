package com.unq.edu.tpi.tip.backend.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MercadoPagoInvoiceDTO {
    private Long tableId;
    private Float price;

}
