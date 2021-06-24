package com.unq.edu.tpi.tip.backend.services;

import com.mercadopago.exceptions.MPException;
import com.unq.edu.tpi.tip.backend.models.dtos.MercadoPagoURLDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MercadoPagoServiceTest
{
	@InjectMocks
	private MercadoPagoService mercadoPagoService;

	@Test
	public void createAndRedirect() throws IOException, MPException
	{
		MercadoPagoURLDTO mercadoPagoURLDTO = mercadoPagoService.createAndRedirect(1l, 100.00f);
		assertNotNull(mercadoPagoURLDTO);
	}
}
