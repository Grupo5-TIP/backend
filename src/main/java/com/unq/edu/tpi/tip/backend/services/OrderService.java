package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.TableNotFoundException;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.models.Order;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService
{
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
		this.orderMapper = new OrderMapper();
	}

	public OrderDTO getOrderByTableID(Long tableId) throws TableNotFoundException {
		Order order = this.orderRepository.findByTableId(tableId).orElseThrow(
				() -> new TableNotFoundException(tableId));
		return OrderMapper.mapEntityIntoDTO(order);
	}
}
