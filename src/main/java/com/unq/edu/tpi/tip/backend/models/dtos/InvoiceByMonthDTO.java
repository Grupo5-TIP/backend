package com.unq.edu.tpi.tip.backend.models.dtos;

import lombok.Getter;
import lombok.Setter;

public class InvoiceByMonthDTO {
    @Getter
    @Setter
    private String month;
    @Getter
    @Setter
    private Integer year;
    @Getter
    @Setter
    private Double totalAmmount;

    public InvoiceByMonthDTO(Integer year, String month) {
        this.month = month;
        this.year = year;
        this.totalAmmount = 0.0;
    }

}
