package com.unq.edu.tpi.tip.backend.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.services.OrderTableService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class OrderTableControllerTest  extends TemplateControllerTest{

	@InjectMocks
	private OrderTableController orderTableController;

	@Mock
	private OrderTableService orderTableService;

	@Before
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(orderTableController)
				.build();
	}

	@Test
	public void getAllOrders() throws Exception
	{
		when(orderTableService.getAll()).thenReturn(new ArrayList<>());

		MvcResult result = mockMvc.perform(get("/api/tables/"))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		Object object = mapper.readValue(response.getBytes(), List.class);
		assertTrue(object instanceof List);
		assertEquals(((List<?>) object).size(), 0);
	}

}
