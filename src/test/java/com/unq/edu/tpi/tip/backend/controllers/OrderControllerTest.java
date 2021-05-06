package com.unq.edu.tpi.tip.backend.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest extends TemplateControllerTest {
	List<OrderDTO> orderDTOS;

	@InjectMocks
	private OrderController orderController;

	@Mock
	private OrderService orderService;

	@Mock
	private OrderMapper orderMapper;

	@Before
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(orderController)
				.build();
		orderDTOS = new ArrayList<>();
	}

	@Test
	public void getAllOrders() throws Exception
	{
		when(orderService.getAll()).thenReturn(orderDTOS);

		MvcResult result = mockMvc.perform(get("/api/orders/"))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		Object object = mapper.readValue(response.getBytes(), List.class);
		assertTrue(object instanceof List);
		assertEquals(((List<?>) object).size(), 0);
	}

	@Test (expected = NestedServletException.class)
	public void whenTryToGetOrdersByTableIDWhichDoesNotExistThrows404Status() throws Exception
	{
		when(orderService.getOrdersByTableID(1L)).thenThrow(new TableDoesNotHaveOrdersException(1L));

		mockMvc.perform(get("/api/orders/1"))
				.andExpect(status().isNotFound())
				.andReturn();

		//TODO revisar porque dvuelve un NestedServletException en lugar de TableDoesNotHaveOrdersException
	}

	@Test
	public void whenTryToGetOrdersByTableIDWhichExistReturn200Status() throws Exception
	{
		OrderDTO orderDTO = new OrderDTO();

		orderDTOS = Arrays.asList(orderDTO);
		when(orderService.getOrdersByTableID(1L)).thenReturn(orderDTOS);

		MvcResult result = mockMvc.perform(get("/api/orders/1"))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		Object object = mapper.readValue(response.getBytes(), List.class);
		assertTrue(object instanceof List);
		assertEquals(((List<?>) object).size(), 1);
	}


	@Test
	public void createUser() throws Exception
	{
		//TODO Revisar si es suficiente aunque el response sea vacio por no tener objetos o se necesita crear una clase
		// separada para tests de integración con objetos reales

		Item orderedItems = mock(Item.class);
		OrderDTO orderDTO = new OrderDTO();
		//orderDTO.setId(1L);
		//orderDTO.setTableId(1L);
		//orderDTO.setOrderedItems(Arrays.asList(orderedItems));
		orderDTO.setOrderedItems(new ArrayList<>());
		CustomerOrder customerOrder =  new CustomerOrder(); //mock(CustomerOrder.class);


		//when(orderService.createOrder(orderDTO)).thenReturn(orderDTO);
		//when(orderMapper.mapToPojo(orderDTO)).thenReturn(customerOrder);

		MvcResult result = mockMvc.perform(
				post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(asJsonString(orderDTO)))
				.andExpect(status().isCreated())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		//Object object = mapper.readValue(response.getBytes(), OrderDTO.class);
		//assertTrue(object instanceof UserApi);
	}
}
