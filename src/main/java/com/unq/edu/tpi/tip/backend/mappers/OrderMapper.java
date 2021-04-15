package com.unq.edu.tpi.tip.backend.mappers;

import com.unq.edu.tpi.tip.backend.models.Order;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public List<OrderDTO> mapEntitiesIntoDTOs(Iterable<Order> entities) {
        List<OrderDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));

        return dtos;
    }

    public static OrderDTO mapEntityIntoDTO(Order order)
    {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setTableId(order.getTableId());
        orderDTO.setOrderedItems(order.getOrderedItems());
        return orderDTO;
    }

    public Order mapToPojo(OrderDTO orderDTO)
    {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setTableId(orderDTO.getTableId());
        order.setOrderedItems(orderDTO.getOrderedItems());
        return order;
    }

    public OrderDTO mapToDTO(Order order)
    {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setTableId(order.getTableId());
        orderDTO.setOrderedItems(order.getOrderedItems());
        return orderDTO;
    }
}
