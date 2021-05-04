package com.unq.edu.tpi.tip.backend.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderTableDTO {
    private Long id;
    private String state;
    private Integer x;
    private Integer y;

}
