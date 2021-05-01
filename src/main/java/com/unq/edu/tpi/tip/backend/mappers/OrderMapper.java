package com.unq.edu.tpi.tip.backend.mappers;

import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public List<OrderDTO> mapEntitiesIntoDTOs(Iterable<CustomerOrder> entities) {
        List<OrderDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));

        return dtos;
    }

    public static OrderDTO mapEntityIntoDTO(CustomerOrder customerOrder)
    {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(customerOrder.getId());
        orderDTO.setTableId(customerOrder.getTableId());
        orderDTO.setOrderedItems(customerOrder.getOrderedItems());
        return orderDTO;
    }

    public CustomerOrder mapToPojo(OrderDTO orderDTO)
    {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(orderDTO.getId());
        customerOrder.setTableId(orderDTO.getTableId());
        customerOrder.setOrderedItems(orderDTO.getOrderedItems());
        return customerOrder;
    }

    public OrderDTO mapToDTO(CustomerOrder customerOrder)
    {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(customerOrder.getId());
        orderDTO.setTableId(customerOrder.getTableId());
        orderDTO.setOrderedItems(customerOrder.getOrderedItems());
        return orderDTO;
    }
}
