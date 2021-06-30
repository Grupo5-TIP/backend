package com.unq.edu.tpi.tip.backend.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceTest
{
	@Test
	public void testGetterAndSetter(){
		Invoice invoice = new Invoice();
		invoice.setCustomerOrder(new ArrayList<>());
		invoice.setPaymentType("paymentType");
		invoice.setPrefix(1L);

		assertEquals(invoice.getCustomerOrder().size(), 0);
		assertNull(invoice.getNumber());
		assertEquals(invoice.getPaymentType(), "paymentType");
		assertEquals(invoice.getPrefix(), 1L, 0);
		assertNull(invoice.getId());
	}
}
