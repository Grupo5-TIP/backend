package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.models.dtos.ProductDTO;
import com.unq.edu.tpi.tip.backend.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest extends TemplateControllerTest{
	@InjectMocks
	private ProductController productController;

	@Mock
	private ProductService productService;

	@Before
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(productController)
				.build();
	}

	@Test
	public void getAllOrders() throws Exception
	{

		when(productService.getAll()).thenReturn(new HashMap());

		MvcResult result = mockMvc.perform(get("/api/products/"))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		Object object = mapper.readValue(response.getBytes(), HashMap.class);
		assertTrue(object instanceof HashMap);
		assertEquals(((Map<String, List<ProductDTO>>) object).size(), 0);
	}
}
