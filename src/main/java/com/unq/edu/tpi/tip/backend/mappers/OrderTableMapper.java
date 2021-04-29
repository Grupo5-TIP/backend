package com.unq.edu.tpi.tip.backend.mappers;

import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderTableDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderTableMapper {
    public List<OrderTableDTO> mapEntitiesIntoDTOs(Iterable<OrderTable> entities) {
        List<OrderTableDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));

        return dtos;
    }

    public static OrderTableDTO mapEntityIntoDTO(OrderTable orderTable)
    {
        OrderTableDTO orderTableDTO = new OrderTableDTO();
        orderTableDTO.setId(orderTable.getId());
        return orderTableDTO;
    }

    public OrderTableDTO mapToDTO(OrderTable orderTable)
    {
        OrderTableDTO orderTableDTO = new OrderTableDTO();
        orderTableDTO.setId(orderTable.getId());
        orderTableDTO.setState(orderTable.getState().toString());
        return orderTableDTO;
    }
}
