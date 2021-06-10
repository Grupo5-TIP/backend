package com.unq.edu.tpi.tip.backend.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unq.edu.tpi.tip.backend.controllers.OrderController;
import com.unq.edu.tpi.tip.backend.exceptions.StateNotFoundException;
import com.unq.edu.tpi.tip.backend.models.*;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderRepository;
import com.unq.edu.tpi.tip.backend.services.OrderService;
import com.unq.edu.tpi.tip.backend.services.OrderTableService;
import com.unq.edu.tpi.tip.backend.services.ProductService;
import com.unq.edu.tpi.tip.backend.services.StateService;
import org.aspectj.weaver.ast.Or;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
		WebConfig.class
})
public class OrderControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private StateService stateService;
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private ProductService productService;

	private MockMvc mockMvc;
	private ObjectMapper mapper;
	private Product aProduct;

	@Before
	public void setup() throws StateNotFoundException
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		this.mapper = new ObjectMapper()
				.findAndRegisterModules()
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		aProduct = new Product("Agua tonica", "arrolla la sed", 100.0, "", "Bebidas");
		productService.createProduct(aProduct);

		AvailableState available = new AvailableState();
		available.setId(2L);
		stateService.save(available);
		OrderTable orderTable1 = new OrderTable(stateService.findById(1L), 1, 10, 4);
		orderTable1.setId(1L);
		orderTableService.save(orderTable1);


	}

	@Test
	public void createOrderIntegrationTest() throws Exception{

		Long tableId = 1L ;
		Item item = new Item(2, aProduct);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setTableId(tableId);
		orderDTO.setOrderedItems(Arrays.asList(item));

		MvcResult orderResult = mockMvc.perform(post("/api/orders")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(orderDTO)))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		OrderDTO orderSavedDTO = mapper.readValue(orderResult.getResponse().getContentAsString(), OrderDTO.class);
		assertNotNull(orderResult);
		assertEquals(orderDTO.getTableId(), orderSavedDTO.getTableId());
		assertEquals(orderDTO.getOrderedItems(), orderSavedDTO.getOrderedItems());
	}

	public  String asJsonString(final Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}