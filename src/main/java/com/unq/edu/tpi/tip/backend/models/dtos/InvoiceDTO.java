package com.unq.edu.tpi.tip.backend.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceDTO {
    private Long id;
    private Long prefix;
    private Long number;
    private String paymentType;
}
