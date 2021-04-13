package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.TableNotFoundException;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.models.Order;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService
{
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
		this.orderMapper = new OrderMapper();
	}

	public List<OrderDTO> getOrdersByTableID(Long tableId) throws TableNotFoundException {
		List<Order> orders = this.orderRepository.findAllByTableId(tableId).orElseThrow(
				() -> new TableNotFoundException(tableId));

		return orderMapper.mapEntitiesIntoDTOs(orders);
	}

	public OrderDTO createOrder(OrderDTO orderDTO)
	{
		Order order = orderMapper.mapToPojo(orderDTO);
		this.orderRepository.save(order);

		return orderMapper.mapToDTO(order);
	}
}
