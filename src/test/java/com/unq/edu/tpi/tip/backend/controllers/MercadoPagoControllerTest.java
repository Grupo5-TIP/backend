package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.MercadoPagoInvoiceDTO;
import com.unq.edu.tpi.tip.backend.services.MercadoPagoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MercadoPagoControllerTest extends TemplateControllerTest {
    @InjectMocks
    private MercadoPagoController mercadoPagoController;

    @Mock
    private MercadoPagoService mercadoPagoService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mercadoPagoController)
                .build();
    }

    @Test
    public void createMercadoPagoTest()  throws Exception {
        MercadoPagoInvoiceDTO invoiceDTO = new MercadoPagoInvoiceDTO();
        invoiceDTO.setPrice(100F);
        invoiceDTO.setTableId(1L);

        MvcResult result = mockMvc.perform(
                post("/api/mp/createlink").contentType(MediaType.APPLICATION_JSON).content(asJsonString(invoiceDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertNotNull(response);
    }
}
