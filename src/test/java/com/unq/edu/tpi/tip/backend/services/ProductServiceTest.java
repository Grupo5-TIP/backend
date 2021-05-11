package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.mappers.ProductMapper;
import com.unq.edu.tpi.tip.backend.models.Product;
import com.unq.edu.tpi.tip.backend.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductMapper productMapper;
	@Mock
	private ProductRepository productRepository;

	@Test
	public void whenIGetAllProductsMustInteractOneTimeWithMapperAndRepository(){
		productService.getAll();
		verify(productMapper, times(1)).mapEntitiesIntoDTOs(anyList());
		verify(productRepository,times(1)).findAll();
	}

	@Test
	public void whenICreateAProductMustHasTheSameParamValuesAndMustInteractOneTimeWithMapperAndRepository(){
		Product productMock = mock(Product.class);
		when(productMock.getPrice()).thenReturn(10.00);
		when(productMock.getImage()).thenReturn("img");
		when(productMock.getDescription()).thenReturn("descrp");
		when(productMock.getItems()).thenReturn(new ArrayList<>());

		Product productSaved = productService.createProduct(productMock);

		verify(productRepository,times(1)).save(any(Product.class));
		assertNotNull(productSaved);
		assertEquals(productSaved.getDescription(), productMock.getDescription());
		assertEquals(productSaved.getImage(), productMock.getImage());
		assertEquals(productSaved.getPrice(), productMock.getPrice());
		assertEquals(productSaved.getItems(), productMock.getItems());
	}


}
