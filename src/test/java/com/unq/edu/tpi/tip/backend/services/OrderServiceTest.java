package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

	CustomerOrder customerOrderMock;
	OrderDTO orderDTOMock;

	@Before
	public void setUp(){
		customerOrderMock = mock(CustomerOrder.class);
		orderDTOMock = mock(OrderDTO.class);
	}

	@Test (expected = TableDoesNotHaveOrdersException.class)
	public void whenIGetOrdersByTableIDWhichDoesNotExistReturnAnEmptyList()
			throws TableDoesNotHaveOrdersException{
		List<OrderDTO> orderDTOS = orderService.getOrdersByTableID(1l);
	}

	@Test
	public void whenIGetOrdersByTableID1ReturnsAndEmptyOrdersFromThatTable()
			throws TableDoesNotHaveOrdersException{
		List<CustomerOrder> customerOrders = new ArrayList<>();
		List<OrderDTO> ordersDTO = new ArrayList<>();

		when(orderRepository.findAllByTableId(anyLong())).thenReturn(Optional.of(customerOrders));

		List<OrderDTO> orderExpected = orderService.getOrdersByTableID(1l);

		verify(orderRepository, times(1)).findAllByTableId(anyLong());
		assertEquals(orderExpected, ordersDTO);
	}

	@Test
	public void whenIGetOrdersByTableID1ReturnsTheOrdersFromThatTable()
			throws TableDoesNotHaveOrdersException{

		when(customerOrderMock.getIsChecked()).thenReturn(false);
		List<CustomerOrder> customerOrders = Arrays.asList(customerOrderMock);
		List<OrderDTO> orderExpected = Arrays.asList(orderDTOMock);

		when(orderRepository.findAllByTableId(anyLong())).thenReturn(Optional.of(customerOrders));
		when(orderMapper.mapEntitiesIntoDTOs(customerOrders)).thenReturn(orderExpected);


		orderExpected = orderService.getOrdersByTableID(1L);

		verify(orderRepository, times(1)).findAllByTableId(anyLong());
		assertNotNull(orderExpected);
		assertEquals(orderExpected.get(0), orderDTOMock);
	}

	@Test (expected = OrderEmptyException.class)
	public void whenITryToCreateAnOrderWithEmptyOrderedItemsRaiseAnOrderEmptyException() throws OrderEmptyException
	{
		when(orderMapper.mapToPojo(orderDTOMock)).thenReturn(customerOrderMock);
		when(customerOrderMock.hasOrderedItems()).thenReturn(false);
		orderService.createOrder(orderDTOMock);
	}

	@Test
	public void whenIGetOrdersByTableID1ReturnsTheOrdersWithOneOrderFromThatTableReturnsAnEmptyListWhenTheOrderIsChecked()
			throws TableDoesNotHaveOrdersException{
		CustomerOrder customerOrderMock = mock(CustomerOrder.class);
		when(customerOrderMock.getIsChecked()).thenReturn(true);
		List<CustomerOrder> customerOrders = Arrays.asList(customerOrderMock);

		when(orderRepository.findAllByTableId(anyLong())).thenReturn(Optional.of(customerOrders));

		List<OrderDTO> orderExpected = orderService.getOrdersByTableID(1l);

		verify(orderRepository, times(1)).findAllByTableId(anyLong());
		assertEquals(orderExpected.isEmpty(), true);
	}


	@Test
	public void whenICreateAnOrderWithOrderedItemsMustSaveTwiceIntoDatabase() throws OrderEmptyException
	{
		OrderDTO orderMock = mock(OrderDTO.class);
		CustomerOrder customerOrder = mock(CustomerOrder.class);
		Item itemMock = mock(Item.class);

		when(orderMapper.mapToPojo(any(OrderDTO.class))).thenReturn(customerOrder);

		when(customerOrder.hasOrderedItems()).thenReturn(true);
		when(customerOrder.getOrderedItems()).thenReturn(Arrays.asList(itemMock));


		when(orderRepository.save(any(CustomerOrder.class))).thenReturn(customerOrder);

		orderService.createOrder(orderMock);

		verify(orderRepository, times(2)).save(any(CustomerOrder.class));
		verify(itemMock, times(1)).setCustomerOrder(customerOrder);
		verify(orderMapper, times(1)).mapEntityIntoDTO(customerOrder);
	}

	@Test
	public void listAnEmptyListWhenGetAllOrdersFromDatabaseWhen()
	{
		List<OrderDTO> orderDTOS = orderService.getAll();

		Iterable<CustomerOrder> customerOrders = new ArrayList<>();
		assertNotNull(orderDTOS);
		assertTrue(orderDTOS.isEmpty());
		verify(orderRepository, times(1)).findAll();
		verify(orderMapper, times(1)).mapEntitiesIntoDTOs(customerOrders);
	}


}
