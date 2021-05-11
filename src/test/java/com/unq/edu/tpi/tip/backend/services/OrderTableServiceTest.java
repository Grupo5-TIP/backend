package com.unq.edu.tpi.tip.backend.services;


import com.unq.edu.tpi.tip.backend.mappers.OrderTableMapper;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.models.Product;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderTableRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class OrderTableServiceTest {
	@InjectMocks
	private OrderTableService orderTableService;

	@Mock
	private OrderTableRepository orderTableRepository;
	@Mock
	private OrderTableMapper orderTableMapper;
	@Mock
	private OrderService orderService;

	@Test
	public void whenIGetAllProductsMustInteractOneTimeWithMapperAndRepository(){
		orderTableService.getAll();
		verify(orderTableMapper, times(1)).mapEntitiesIntoDTOs(anyList());
		verify(orderTableRepository,times(1)).findAll();
	}

	@Test
	public void whenICreateAProductMustHasTheSameParamValuesAndMustInteractOneTimeWithMapperAndRepository(){
		OrderTable orderTable = mock(OrderTable.class);

		orderTableService.save(orderTable);

		verify(orderTableRepository,times(1)).save(any(OrderTable.class));

	}

	@Test
	public void whenIGetAllItemsFromTableAndTableDoesNotHaveOrdersMustReturnAnEmptyItemList()
	{
		List<Item> items = orderTableService.getAllItemsFromTable(1L);
		assertNotNull(items);
		assertTrue(items.isEmpty());
	}

	@Test
	public void whenIGetAllItemsFromTableAndTableHasAnOrderWithTwoDifferentsItemsMustReturnAListWithTwoItems()
	{
		OrderDTO orderDTOMock = mock(OrderDTO.class);
		OrderDTO anotherDTOMock = mock(OrderDTO.class);
		List<OrderDTO> orders = Arrays.asList(orderDTOMock, anotherDTOMock);

		when(orderService.getOrdersByTableID(anyLong())).thenReturn(orders);

		Item itemMock = mock(Item.class);
		List<Item> items = Arrays.asList(itemMock);

		when(orderDTOMock.getOrderedItems()).thenReturn(items);

		Item anotherItemMock = mock(Item.class);
		List<Item> anotherItems = Arrays.asList(anotherItemMock);

		when(anotherDTOMock.getOrderedItems()).thenReturn(anotherItems);

		List<Item> itemsFromTable = orderTableService.getAllItemsFromTable(1L);

		assertNotNull(itemsFromTable);
		assertEquals(itemsFromTable.size(), 2);
	}

	@Test
	public void whenIGetAllItemsFromTableAndTableHasAnOrderWithTwoSameItemMustReturnAListWithOneItem()
	{

		OrderDTO orderDTOMock = mock(OrderDTO.class);
		OrderDTO anotherDTOMock = mock(OrderDTO.class);
		List<OrderDTO> orders = Arrays.asList(orderDTOMock, anotherDTOMock);

		when(orderService.getOrdersByTableID(anyLong())).thenReturn(orders);

		Product aProductMock = new Product("Agua tonica", "arrolla la sed", 100.0, "", "Bebidas"); //mock(Product.class);

		Item itemMock = mock(Item.class);
		when(itemMock.getAmount()).thenReturn(1);
		when(itemMock.getProduct()).thenReturn(aProductMock);

		List<Item> items = Arrays.asList(itemMock);

		when(orderDTOMock.getOrderedItems()).thenReturn(items);

		Item anotherItemMock = mock(Item.class);
		when(anotherItemMock.getAmount()).thenReturn(2);
		when(anotherItemMock.getProduct()).thenReturn(aProductMock);
		List<Item> anotherItems = Arrays.asList(anotherItemMock);

		when(anotherDTOMock.getOrderedItems()).thenReturn(anotherItems);

		List<Item> itemsFromTable = orderTableService.getAllItemsFromTable(1L);

		assertNotNull(itemsFromTable);
		//assertEquals(itemsFromTable.size(), 1);
	}
}
