package com.unq.edu.tpi.tip.backend.mappers;

import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderTableDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderTableMapper {
    public List<OrderTableDTO> mapEntitiesIntoDTOs(Iterable<OrderTable> entities) {
        List<OrderTableDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(this.mapEntityIntoDTO(e)));

        return dtos;
    }

    public OrderTableDTO mapEntityIntoDTO(OrderTable orderTable)
    {
        OrderTableDTO orderTableDTO = new OrderTableDTO();
        orderTableDTO.setId(orderTable.getId());
        orderTableDTO.setState(orderTable.getState().getState());
        orderTableDTO.setX(orderTable.getX());
        orderTableDTO.setY(orderTable.getY());
        orderTableDTO.setSize(orderTable.getSize());

        return orderTableDTO;
    }

}
