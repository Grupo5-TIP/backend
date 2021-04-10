package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.TableNotFoundException;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService
{
	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository){
		this.orderRepository = orderRepository;
	}

	public OrderDTO getOrderByTableID(Long tableId) throws TableNotFoundException
	{
		return this.orderRepository.findByTableId(tableId).orElseThrow(
				() -> new TableNotFoundException(tableId)
		);
	}
}
