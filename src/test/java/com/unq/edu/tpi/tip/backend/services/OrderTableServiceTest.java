package com.unq.edu.tpi.tip.backend.services;


import com.unq.edu.tpi.tip.backend.exceptions.OrderDoesNotExistException;
import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.mappers.OrderTableMapper;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.models.Product;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
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

	@Mock
	OrderTable orderTableMock;
	@Mock
	OrderDTO orderDTOMock;
	@Mock
	OrderDTO anotherDTOMock;

	List<OrderDTO> orders;

	@Before
	public void setUp(){
		orders = Arrays.asList(orderDTOMock, anotherDTOMock);
	}

	@Test
	public void whenIGetAllProductsMustInteractOneTimeWithMapperAndRepository(){
		orderTableService.getAll();
		verify(orderTableMapper, times(1)).mapEntitiesIntoDTOs(anyList());
		verify(orderTableRepository,times(1)).findAll();
	}

	@Test
	public void whenICreateAProductMustHasTheSameParamValuesAndMustInteractOneTimeWithMapperAndRepository(){
		orderTableService.save(orderTableMock);
		verify(orderTableRepository,times(1)).save(any(OrderTable.class));

	}

	@Test(expected = TableDoesNotExistException.class)
	public void whenIGetAllItemsFromTableAndTableDoesNotHaveOrdersReturnAnEmptyItemList()
			throws TableDoesNotExistException
	{
		orderTableService.getAllItemsFromTable(1L);
	}

	@Test
	public void whenIGetAllItemsFromTableAndTableHasAnOrderWithTwoDifferentItemsMustReturnAListWithTwoItems()
			throws TableDoesNotExistException
	{

		when(orderService.getOrdersByTableID(anyLong())).thenReturn(orders);
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.of(mock(OrderTable.class)) );
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
			throws TableDoesNotExistException
	{

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
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.of(mock(OrderTable.class)) );

		List<Item> itemsFromTable = orderTableService.getAllItemsFromTable(1L);

		assertNotNull(itemsFromTable);
		assertEquals(itemsFromTable.get(0).getProduct(), aProductMock);
		assertEquals(itemsFromTable.get(0).getAmount(), 3);
		assertEquals(itemsFromTable.size(), 1);
	}

	@Test
	public void testRequestBill() throws TableDoesNotExistException
	{
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.of(orderTableMock));

		orderTableService.requestBill(anyLong());
		verify(orderTableRepository, times(1)).save(orderTableMock);
		verify(orderTableMock, times(1)).changeToRequestBillState();
	}

	@Test(expected = TableDoesNotExistException.class)
	public void testRequestBillRaiseTableDoesNotExistExceptionWhenTableDoesNotExist() throws TableDoesNotExistException
	{
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

		orderTableService.requestBill(anyLong());
		verify(orderTableRepository, times(1)).save(orderTableMock);
		verify(orderTableRepository, times(1)).findById(anyLong());

	}

	@Test(expected = TableDoesNotExistException.class)
	public void testDeleteAllOrdersFromTableRaiseTableDoesNotExistExceptionWhenTableDoesNotExist()
			throws TableDoesNotExistException, OrderDoesNotExistException
	{
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
		orderTableService.deleteAllOrdersFromTable(anyLong());
		verify(orderTableRepository, times(1)).findById(anyLong());
	}

	@Test
	public void testDeleteAllOrders()
			throws TableDoesNotExistException, OrderDoesNotExistException
	{
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
		when(orderService.getOrdersByTableID(anyLong())).thenReturn(orders);
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.of(orderTableMock));

		orderTableService.deleteAllOrdersFromTable(anyLong());

		verify(orderTableRepository, times(2)).findById(anyLong());
		verify(orderService, times(1)).getOrdersByTableID(anyLong());
		verify(orderService, atLeastOnce()).deleteOrder(anyLong());
		verify(orderTableMock, times(1)).setAvailableState();
		verify(orderTableRepository, times(1)).save(orderTableMock);
	}

	@Test(expected = TableDoesNotExistException.class)
	public void updateTableOrderTableDoesNotExistExceptionWhenTableDoesNotExist()
			throws OrderEmptyException, TableDoesNotExistException, OrderDoesNotExistException
	{
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

		orderTableService.updateTableOrder(1l, new ArrayList<>());
		verify(orderTableRepository, times(1)).findById(anyLong());
	}

	@Test
	public void updateTableOrderTable()
			throws OrderEmptyException, TableDoesNotExistException, OrderDoesNotExistException
	{
		when(orderTableRepository.findById(anyLong())).thenReturn(Optional.ofNullable(orderTableMock));

		orderTableService.updateTableOrder(1l, new ArrayList<>());
		verify(orderTableRepository, atLeastOnce()).findById(anyLong());
		verify(orderService, times(1)).createOrder(any(OrderDTO.class));
	}

	//@Test (expected = TableDoesNotExistException.class)
	public void changeStateToMercadoPagoRaiseTableDoesNotExistException() throws TableDoesNotExistException {
		//orderTableRepository.findById(1l).orElseThrow(
		//		()-> new TableDoesNotExistException(1l));


		//when(orderTableRepository.findById(1l)).thenReturn(Optional.of(null));
		when(orderTableRepository.findById(1l)).thenThrow(new TableDoesNotExistException(1l));
		orderTableService.changeStateToMercadoPago(1l);

		//verify(orderTableRepository)
	}

	@Test
	public void changeStateToMercadoPago() throws TableDoesNotExistException {
		when(orderTableRepository.findById(1l)).thenReturn(Optional.of(orderTableMock));
		orderTableService.changeStateToMercadoPago(1l);

		verify(orderTableRepository, times(2)).findById(1l);
		verify(orderTableRepository, times(1)).save(orderTableMock);
	}
}
