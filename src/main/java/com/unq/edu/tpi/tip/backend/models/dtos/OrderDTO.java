package com.unq.edu.tpi.tip.backend.models.dtos;

import com.unq.edu.tpi.tip.backend.models.OrderedItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Long tableId;
    private Set<OrderedItem> orderedItems;
}
