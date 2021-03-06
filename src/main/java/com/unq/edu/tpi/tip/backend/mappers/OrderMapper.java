package com.unq.edu.tpi.tip.backend.mappers;

import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
    public List<OrderDTO> mapEntitiesIntoDTOs(Iterable<CustomerOrder> entities) {
        List<OrderDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(this.mapEntityIntoDTO(e)));

        return dtos;
    }

    public CustomerOrder mapToPojo(OrderDTO orderDTO)
    {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(orderDTO.getId());
        customerOrder.setTableId(orderDTO.getTableId());
        customerOrder.setOrderedItems(orderDTO.getOrderedItems());
        return customerOrder;
    }

    public OrderDTO mapEntityIntoDTO(CustomerOrder customerOrder)
    {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(customerOrder.getId());
        orderDTO.setTableId(customerOrder.getTableId());
        orderDTO.setOrderedItems(customerOrder.getOrderedItems());
        return orderDTO;
    }
}
