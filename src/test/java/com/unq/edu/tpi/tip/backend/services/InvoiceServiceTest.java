package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.InvoiceDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.mappers.InvoiceMapper;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.Invoice;
import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.InvoiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest
{
	@InjectMocks
	InvoiceService invoiceService;

	@Mock
	private InvoiceRepository invoiceRepository;
	@Mock
	private InvoiceMapper invoiceMapper;

	@Mock
	private OrderMapper orderMapper;

	@Mock
	private OrderService orderService;

	@Test
	public void createInvoiceTest() throws TableDoesNotExistException, InvoiceDoesNotHaveOrdersException
	{
		OrderDTO orderDTO = mock(OrderDTO.class);
		List< OrderDTO > orders = Arrays.asList(orderDTO);
		when(orderService.getOrdersByTableID(anyLong())).thenReturn(orders);
		when(orderMapper.mapDTOSIntoEntities(anyList())).thenReturn(new ArrayList<>());
		InvoiceDTO invoiceDTO = mock(InvoiceDTO.class);

		invoiceService.createInvoice(anyLong(), invoiceDTO );

		verify( orderService, times(1)).checkBill(anyLong());
		verify( invoiceRepository, times(1)).save(any(Invoice.class));
	}

	@Test(expected = InvoiceDoesNotHaveOrdersException.class)
	public void createInvoiceRaiseExceptionWhenOrdersIsEmpty() throws TableDoesNotExistException, InvoiceDoesNotHaveOrdersException
	{
		List< OrderDTO > orders = new ArrayList<>();
		when(orderService.getOrdersByTableID(anyLong())).thenReturn(orders);

		InvoiceDTO invoiceDTO = mock(InvoiceDTO.class);

		invoiceService.createInvoice(anyLong(),invoiceDTO );
	}
}
