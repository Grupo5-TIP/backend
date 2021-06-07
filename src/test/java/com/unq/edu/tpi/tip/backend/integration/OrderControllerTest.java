package com.unq.edu.tpi.tip.backend.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unq.edu.tpi.tip.backend.controllers.OrderController;
import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.Product;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderRepository;
import com.unq.edu.tpi.tip.backend.services.OrderService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class OrderControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		this.mapper = new ObjectMapper()
				.findAndRegisterModules()
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Test
	public void createOrderIntegrationTest() throws Exception{

		Long tableId = 1L ;
		Product aProduct = new Product("Agua tonica", "arrolla la sed", 100.0, "", "Bebidas");
		Item item = new Item(2, aProduct);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setTableId(tableId);
		orderDTO.setOrderedItems(Arrays.asList(item));

	//MockMvcRequestBuilders
		MvcResult orderResult = mockMvc.perform(post("/api/orders")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(orderDTO)))
				//.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		//OrderDTO order = mapper.readValue(orderResult.getResponse().getContentAsString(), OrderDTO.class);
		OrderDTO order = mapper.readValue(orderResult.getRequest().getInputStream(), OrderDTO.class);
		String response = orderResult.getResponse().getContentAsString();
		assertNotNull(orderResult);
	}

	public  String asJsonString(final Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

 */
/*
//@SpringBootTest
//@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest
@RunWith(SpringRunner.class)
public class OrderControllerTest
{

	private OrderController orderController;

	//@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	//@Autowired
	private MockMvc mockMvc;
	//@Autowired
	private ObjectMapper mapper;
	//@Autowired
	private MockMvc mockMvc;

	//@Before
	public void setUp(){
				mapper = new ObjectMapper()
				.findAndRegisterModules()
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	}

	@Test
	public void createOrderIntegrationTest() throws Exception{

		Long tableId = 1L ;
		Product aProduct = new Product("Agua tonica", "arrolla la sed", 100.0, "", "Bebidas");
		Item item = new Item(2, aProduct);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setTableId(tableId);
		orderDTO.setOrderedItems(Arrays.asList(item));


		MvcResult orderResult = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/orders/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(orderDTO)))
				//.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		//OrderDTO order = mapper.readValue(orderResult.getResponse().getContentAsString(), OrderDTO.class);
		String response = orderResult.getResponse().getContentAsString();
		//assertNotNull(order);
	}

	public  String asJsonString(final Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

*/
/*
@RunWith(SpringRunner.class)
@WebAppConfiguration
//@AutoConfigureMockMvc
@SpringBootTest//(classes = OrderController.class)
//@ContextConfiguration
public class CreateOrderIntegrationTest extends TemplateControllerTest
{

	//@InjectMocks
	private OrderController orderController;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup(){
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)//..standaloneSetup(orderController)
				.build();
	}

	@Test
	public void createOrderIntegrationTest() throws Exception{

		Long tableId = 1L ;
		Product aProduct = new Product("Agua tonica", "arrolla la sed", 100.0, "", "Bebidas");
		Item item = new Item(2, aProduct);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setTableId(tableId);
		orderDTO.setOrderedItems(Arrays.asList(item));

		//when(orderRepository.save(any())).thenReturn(customerOrder);
		//when(orderService.createOrder(any())).thenReturn(orderDTO);

		MvcResult orderResult = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/orders/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(orderDTO)))
				//.andExpect(status().isCreated())
				//.andDo(print())
				.andReturn();

		OrderDTO order = mapper.readValue(orderResult.getResponse().getContentAsString(), OrderDTO.class);
		String response = orderResult.getResponse().getContentAsString();
		assertNotNull(order);
	}

	public  String asJsonString(final Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
*/