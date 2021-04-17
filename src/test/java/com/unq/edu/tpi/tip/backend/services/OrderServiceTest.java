package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.models.Order;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest
{
	@InjectMocks
	OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderMapper orderMapper;

	@Test(expected = TableDoesNotHaveOrdersException.class)
	public void whenIGetOrdersByTableIDWhichDoesNotExistRaiseATableDoesNotHaveOrdersException()
			throws TableDoesNotHaveOrdersException{
		orderService.getOrdersByTableID(1l);
	}

	@Test
	public void whenIGetOrdersByTableID1ReturnsAndEmptyOrdersFromThatTable()
			throws TableDoesNotHaveOrdersException{
		List<Order> orders = new ArrayList<>();
		List<OrderDTO> ordersDTO = new ArrayList<>();

		when(orderRepository.findAllByTableId(anyLong())).thenReturn(Optional.of(orders));
		when(orderMapper.mapEntitiesIntoDTOs(orders)).thenReturn(ordersDTO);

		List<OrderDTO> orderExpected = orderService.getOrdersByTableID(1l);

		verify(orderRepository, times(1)).findAllByTableId(anyLong());
		verify(orderMapper, times(1)).mapEntitiesIntoDTOs(orders);

		assertEquals(orderExpected, ordersDTO);
	}

	@Test
	public void whenIGetOrdersByTableID1ReturnsTheOrdersFromThatTable()
			throws TableDoesNotHaveOrdersException{
		Order orderMock = mock(Order.class);
		List<Order> orders = Arrays.asList(orderMock);

		OrderDTO orderDTOMock = mock(OrderDTO.class);
		List<OrderDTO> ordersDTO = Arrays.asList(orderDTOMock);

		when(orderRepository.findAllByTableId(anyLong())).thenReturn(Optional.of(orders));
		when(orderMapper.mapEntitiesIntoDTOs(any())).thenReturn(ordersDTO);

		List<OrderDTO> orderExpected = orderService.getOrdersByTableID(1l);

		verify(orderRepository, times(1)).findAllByTableId(anyLong());
		verify(orderMapper, times(1)).mapEntitiesIntoDTOs(anyList());

		assertEquals(orderExpected.get(0).getOrderedItems(), orders.get(0).getOrderedItems());
		assertEquals(orderExpected.get(0).getTableId(), orders.get(0).getTableId());
	}

	@Test (expected = OrderEmptyException.class)
	public void whenITryToCreateAnOrderWithEmptyOrderedItemsRaiseAnOrderEmptyException() throws OrderEmptyException
	{
		OrderDTO orderMock = mock(OrderDTO.class);
		orderService.createOrder(orderMock);
	}
}
