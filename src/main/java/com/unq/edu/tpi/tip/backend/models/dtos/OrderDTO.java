package com.unq.edu.tpi.tip.backend.models.dtos;

import com.unq.edu.tpi.tip.backend.models.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Long tableId;
    private List<Item> orderedItems;
}
