package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceDTO;
import com.unq.edu.tpi.tip.backend.services.InvoiceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceControllerTest  extends TemplateControllerTest
{

	@InjectMocks
	private InvoiceController invoiceController;

	@Mock
	private InvoiceService invoiceService;

	@Before
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(invoiceController)
				.build();
	}

	@Test
	public void createInvoiceTest()  throws Exception{
		InvoiceDTO invoiceDTO = new InvoiceDTO();
		invoiceDTO.setPaymentType("MP");

		MvcResult result = mockMvc.perform(
				post("/api/invoices/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(invoiceDTO)))
				.andExpect(status().isCreated())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
	}

}