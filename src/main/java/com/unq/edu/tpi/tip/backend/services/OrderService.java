package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.OrderDoesNotExistException;
import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.ItemRepository;
import com.unq.edu.tpi.tip.backend.repositories.OrderRepository;
import com.unq.edu.tpi.tip.backend.repositories.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService
{
	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository;
	private final OrderMapper orderMapper;
	private final OrderTableRepository orderTableRepository;

	public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, OrderMapper orderMapper, OrderTableRepository orderTableRepository) {
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
		this.itemRepository = itemRepository;
		this.orderTableRepository= orderTableRepository;
	}

	public List<OrderDTO> getOrdersByTableID(Long tableId) throws TableDoesNotExistException
	{
		orderTableRepository.findById(tableId).orElseThrow(
				()-> new TableDoesNotExistException(tableId));

		List<CustomerOrder> customerOrders = this.orderRepository.findAllByTableId(tableId).orElse(new ArrayList<>());


		customerOrders = customerOrders.stream()
				.filter((order)-> !order.getIsChecked())
				.collect(Collectors.toList());

		return orderMapper.mapEntitiesIntoDTOs(customerOrders);
	}

	@Transactional
	public OrderDTO createOrder(OrderDTO orderDTO) throws OrderEmptyException
	{
		CustomerOrder customerOrder = orderMapper.mapToPojo(orderDTO);
		if (!customerOrder.hasOrderedItems()){
			throw new OrderEmptyException();
		}
		customerOrder = this.orderRepository.save(customerOrder);

		for(Item item : customerOrder.getOrderedItems()){
			item.setCustomerOrder(customerOrder);
		}

		OrderTable table = orderTableRepository.findById(orderDTO.getTableId()).get();
		table.setInUsedState();
		orderTableRepository.save(table);

		customerOrder = this.orderRepository.save(customerOrder);
		return orderMapper.mapEntityIntoDTO(customerOrder);
	}

	@Transactional
	public List<OrderDTO> getAll()
	{
		Iterable<CustomerOrder> customerOrders = this.orderRepository.findAll();
		return orderMapper.mapEntitiesIntoDTOs(customerOrders);
	}

	public void checkBill(Long tableId)
	{
		List<CustomerOrder> customerOrders = this.orderRepository.findAllByTableId(tableId).orElse(new ArrayList<>());

		customerOrders = customerOrders.stream().filter((order) -> !order.getIsChecked()).collect(Collectors.toList());
		for (CustomerOrder order : customerOrders) {
			order.check();
		}
		OrderTable table = orderTableRepository.findById(tableId).get();
		table.setAvailableState();
		orderTableRepository.save(table);
	}
	@Transactional
	public void deleteOrder(Long orderId) throws OrderDoesNotExistException {
		CustomerOrder order = orderRepository.findById(orderId).orElseThrow(
				()-> new OrderDoesNotExistException(orderId));

		orderRepository.delete(order);
	}
}
