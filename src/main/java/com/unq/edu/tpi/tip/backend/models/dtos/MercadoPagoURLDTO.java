package com.unq.edu.tpi.tip.backend.models.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MercadoPagoURLDTO {
    private String url;
    public MercadoPagoURLDTO(String url){
        this.url = url;
    }
}
