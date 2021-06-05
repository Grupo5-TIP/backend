package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.mappers.ProductMapper;
import com.unq.edu.tpi.tip.backend.models.Product;
import com.unq.edu.tpi.tip.backend.models.dtos.ProductDTO;
import com.unq.edu.tpi.tip.backend.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
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


	@Test
	public void whenIGetAllProductsAndRepositoryReturnsTwoProductsFromOneCategoryMustReturnAMapWithOneCategoryAndTwoElements(){

		//Product productMock = mock(Product.class);
		//Product anotherProductMock = mock(Product.class);
		//Iterable<Product> products = Arrays.asList(productMock, anotherProductMock);
		when(productRepository.findAll()).thenReturn(new ArrayList<>());

		ProductDTO productDTOMock = mock(ProductDTO.class);
		ProductDTO anotherProductDTOMock = mock(ProductDTO.class);
		List<ProductDTO> productDTOS = Arrays.asList(productDTOMock, anotherProductDTOMock);
		when(productMapper.mapEntitiesIntoDTOs(anyList())).thenReturn(productDTOS);
		when(productDTOMock.getCategory()).thenReturn("Bebidas");
		when(anotherProductDTOMock.getCategory()).thenReturn("Bebidas");

		Map<String, List<ProductDTO>> products = productService.getAll();

		assertFalse(products.isEmpty());
		assertTrue(products.containsKey("Bebidas"));
		assertEquals(products.get("Bebidas").size(), 2);

		verify(productMapper, times(1)).mapEntitiesIntoDTOs(anyList());
		verify(productRepository,times(1)).findAll();
	}

}
