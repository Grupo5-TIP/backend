package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.OrderDoesNotExistException;
import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderRepository;
import com.unq.edu.tpi.tip.backend.repositories.OrderTableRepository;
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

	@Mock
	private OrderTableRepository orderTableRepository;

	CustomerOrder customerOrderMock;
	OrderDTO orderDTOMock;

	@Before
	public void setUp(){
		customerOrderMock = mock(CustomerOrder.class);
		orderDTOMock = mock(OrderDTO.class);
	}

	@Test (expected = TableDoesNotExistException.class)
	public void whenIGetOrdersByTableIDWhichDoesNotExistReturnAnEmptyList()
			throws TableDoesNotExistException
	{
		List<OrderDTO> orderDTOS = orderService.getOrdersByTableID(1l);
	}

	@Test
	public void whenIGetOrdersByTableID1ReturnsAndEmptyOrdersFromThatTable()
			throws TableDoesNotExistException
	{
		List<CustomerOrder> customerOrders = new ArrayList<>();
		List<OrderDTO> ordersDTO = new ArrayList<>();

		when(orderRepository.findAllByTableId(anyLong())).thenReturn(Optional.of(customerOrders));
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.of(mock(OrderTable.class)) );
		List<OrderDTO> orderExpected = orderService.getOrdersByTableID(1l);

		verify(orderRepository, times(1)).findAllByTableId(anyLong());
		assertEquals(orderExpected, ordersDTO);
	}

	@Test
	public void whenIGetOrdersByTableID1ReturnsTheOrdersFromThatTable()
			throws TableDoesNotExistException
	{

		when(customerOrderMock.getIsChecked()).thenReturn(false);
		List<CustomerOrder> customerOrders = Arrays.asList(customerOrderMock);
		List<OrderDTO> orderExpected = Arrays.asList(orderDTOMock);

		when(orderRepository.findAllByTableId(anyLong())).thenReturn(Optional.of(customerOrders));
		when(orderMapper.mapEntitiesIntoDTOs(customerOrders)).thenReturn(orderExpected);
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.of(mock(OrderTable.class)) );

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
			throws TableDoesNotExistException
	{
		when(customerOrderMock.getIsChecked()).thenReturn(true);
		List<CustomerOrder> customerOrders = Arrays.asList(customerOrderMock);

		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.of(mock(OrderTable.class)) );
		when(orderRepository.findAllByTableId(anyLong())).thenReturn(Optional.of(customerOrders));

		List<OrderDTO> orderExpected = orderService.getOrdersByTableID(1l);

		verify(orderRepository, times(1)).findAllByTableId(anyLong());
		assertEquals(orderExpected.isEmpty(), true);
	}


	@Test
	public void whenICreateAnOrderWithOrderedItemsMustSaveTwiceIntoDatabase() throws OrderEmptyException
	{
		Item itemMock = mock(Item.class);
		OrderTable orderTable = mock(OrderTable.class);
		when(orderMapper.mapToPojo(any(OrderDTO.class))).thenReturn(customerOrderMock);

		when(customerOrderMock.hasOrderedItems()).thenReturn(true);
		when(customerOrderMock.getOrderedItems()).thenReturn(Arrays.asList(itemMock));

		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.of(orderTable));
		when(orderRepository.save(any(CustomerOrder.class))).thenReturn(customerOrderMock);

		orderService.createOrder(orderDTOMock);

		verify(orderRepository, times(2)).save(any(CustomerOrder.class));
		verify(itemMock, times(1)).setCustomerOrder(customerOrderMock);
		verify(orderMapper, times(1)).mapEntityIntoDTO(customerOrderMock);
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

	@Test
	public void checkBillTest(){
		List<CustomerOrder> customerOrders = Arrays.asList(customerOrderMock);
		when(orderRepository.findAllByTableId(anyLong())).thenReturn(Optional.of(customerOrders));

		OrderTable table = mock(OrderTable.class);
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.of(table));

		orderService.checkBill(anyLong());

		verify(orderRepository, times(1)).findAllByTableId(anyLong());
		verify(table, times(1)).setAvailableState();
		verify(customerOrderMock, times(1)).check();

	}

	@Test
	public void deleteOrderWhenTableIdExist() throws OrderDoesNotExistException
	{
		when(orderRepository.findById(anyLong())).thenReturn(Optional.of(customerOrderMock));
		orderService.deleteOrder(anyLong());
		verify(orderRepository, times(1)).findById(anyLong());
	}

	@Test(expected = OrderDoesNotExistException.class)
	public void deleteOrderWhenTableIdExistRaiseOrderDoesNotExistException() throws OrderDoesNotExistException
	{
		when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

		orderService.deleteOrder(anyLong());
		verify(orderRepository, times(1)).findById(anyLong());
	}

}
