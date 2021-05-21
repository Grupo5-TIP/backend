package com.unq.edu.tpi.tip.backend.services;


import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotHaveOrdersException;
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

	@Test(expected = TableDoesNotHaveOrdersException.class)
	public void whenIGetAllItemsFromTableAndTableDoesNotHaveOrdersReturnAnEmptyItemList()
			throws TableDoesNotHaveOrdersException
	{
		orderTableService.getAllItemsFromTable(1L);
	}

	@Test
	public void whenIGetAllItemsFromTableAndTableHasAnOrderWithTwoDifferentItemsMustReturnAListWithTwoItems()
			throws TableDoesNotHaveOrdersException
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
			throws TableDoesNotHaveOrdersException
	{

		OrderDTO orderDTOMock = mock(OrderDTO.class);
		OrderDTO anotherDTOMock = mock(OrderDTO.class);
		List<OrderDTO> orders = Arrays.asList(orderDTOMock, anotherDTOMock);

		Product aProductMock = new Product("Agua tonica", "arrolla la sed", 100.0, "", "Bebidas"); //mock(Product.class);

		Item item = new Item(1, aProductMock);
		//when(itemMock.getAmount()).thenReturn(1);
		//when(itemMock.getProduct()).thenReturn(aProductMock);

		List<Item> items = Arrays.asList(item);

		when(orderDTOMock.getOrderedItems()).thenReturn(items);

		Item anotherItemk = new Item(2, aProductMock);
		//when(anotherItemk.getAmount()).thenReturn(2);
		//when(anotherItemk.getProduct()).thenReturn(aProductMock);
		//when(anotherItemk.equals(any(Item.class))).thenReturn(eq(true));
		List<Item> anotherItems = Arrays.asList(anotherItemk);

		when(anotherDTOMock.getOrderedItems()).thenReturn(anotherItems);

		when(orderService.getOrdersByTableID(anyLong())).thenReturn(orders);

		List<Item> itemsFromTable = orderTableService.getAllItemsFromTable(1L);

		assertNotNull(itemsFromTable);
		assertEquals(itemsFromTable.get(0).getProduct(), aProductMock);
		assertEquals(itemsFromTable.get(0).getAmount(), 3);
		assertEquals(itemsFromTable.size(), 1);
	}
}
