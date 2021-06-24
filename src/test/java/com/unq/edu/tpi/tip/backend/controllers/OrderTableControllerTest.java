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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

	@Test
	public void getAllItemsFromTable() throws Exception
	{
		when(orderTableService.getAllItemsFromTable(1L)).thenReturn(new ArrayList<>());

		MvcResult result = mockMvc.perform(get("/api/tables/items/1"))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		Object object = mapper.readValue(response.getBytes(), List.class);
		assertTrue(object instanceof List);
		assertEquals(((List<?>) object).size(), 0);
	}

	@Test
	public void requestBill() throws Exception
	{
		MvcResult result = mockMvc.perform(get("/api/tables/request/1"))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		verify(orderTableService, times(1)).requestBill(anyLong());
	}

	@Test
	public void deleteAllOrdersFromTable() throws Exception
	{
		MvcResult result = mockMvc.perform(delete("/api/tables/1"))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		verify(orderTableService, times(1)).deleteAllOrdersFromTable(anyLong());
	}

	@Test
	public void updateTableOrder() throws Exception
	{
		Item item = new Item();
		List<Item> items = Arrays.asList(item);

		MvcResult result = mockMvc.perform(put("/api/tables/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(items))
					)
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		verify(orderTableService, times(1)).updateTableOrder(anyLong(), anyList());
	}

}
