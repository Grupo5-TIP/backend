package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotHaveOrders;
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

	public List<OrderDTO> getOrdersByTableID(Long tableId) throws TableDoesNotHaveOrders
	{
		List<Order> orders = this.orderRepository.findAllByTableId(tableId).orElseThrow(
				() -> new TableDoesNotHaveOrders(tableId));

		return orderMapper.mapEntitiesIntoDTOs(orders);
	}

	public OrderDTO createOrder(OrderDTO orderDTO) throws OrderEmptyException
	{
		Order order = orderMapper.mapToPojo(orderDTO);
		if (!order.hasOrderedItems()){
			throw new OrderEmptyException();
		}
		//TODO validar si la mesa existe o no

		order = this.orderRepository.save(order);

		return orderMapper.mapToDTO(order);
	}

	public List<OrderDTO> getAll()
	{
		Iterable<Order> orders = this.orderRepository.findAll();
		return orderMapper.mapEntitiesIntoDTOs(orders);
	}
}
