package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.models.dtos.LoginDTO;
import com.unq.edu.tpi.tip.backend.services.AuthService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest extends TemplateControllerTest
{
	@InjectMocks
	private AuthController authController;

	@Mock
	private AuthService authService;

	@Before
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(authController)
				.build();
	}

	@Test
	public void createInvoiceTest()  throws Exception{
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("username");
		loginDTO.setPassword("password");

		MvcResult result = mockMvc.perform(
				post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(loginDTO)))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
		verify(authService, times(1)).loginWithPassword(anyString(), anyString());
	}


}
