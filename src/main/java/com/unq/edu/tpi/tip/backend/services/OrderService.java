package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.ItemRepository;
import com.unq.edu.tpi.tip.backend.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class OrderService
{
	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository;
	private final OrderMapper orderMapper;

	public OrderService(OrderRepository orderRepository, ItemRepository itemRepository) {
		this.orderRepository = orderRepository;
		this.orderMapper = new OrderMapper();
		this.itemRepository = itemRepository;
	}

	public List<OrderDTO> getOrdersByTableID(Long tableId) throws TableDoesNotHaveOrdersException
	{
		List<CustomerOrder> customerOrders = this.orderRepository.findAllByTableId(tableId).orElseThrow(
				() -> new TableDoesNotHaveOrdersException(tableId));

		return orderMapper.mapEntitiesIntoDTOs(customerOrders);
	}

	public OrderDTO createOrder(OrderDTO orderDTO) throws OrderEmptyException
	{
		CustomerOrder customerOrder = orderMapper.mapToPojo(orderDTO);
		if (!customerOrder.hasOrderedItems()){
			throw new OrderEmptyException();
		}
		//TODO validar si la mesa existe o no

		customerOrder = this.orderRepository.save(customerOrder);
		for(Item items : customerOrder.getOrderedItems()){
			items.setOrder(customerOrder);
		}

		customerOrder = this.orderRepository.save(customerOrder);
		return orderMapper.mapToDTO(customerOrder);
	}

	public List<OrderDTO> getAll()
	{
		Iterable<CustomerOrder> customerOrders = this.orderRepository.findAll();
		return orderMapper.mapEntitiesIntoDTOs(customerOrders);
	}
}
