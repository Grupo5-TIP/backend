package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.mappers.ProductMapper;
import com.unq.edu.tpi.tip.backend.models.Product;
import com.unq.edu.tpi.tip.backend.models.dtos.ProductDTO;
import com.unq.edu.tpi.tip.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService
{
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	public ProductService(ProductRepository productRepository){
		this.productRepository = productRepository;
		productMapper = new ProductMapper();
	}

	public List<ProductDTO> getAll()
	{
		Iterable<Product> products = this.productRepository.findAll();
		List<ProductDTO> productDTOS = this.productMapper.mapEntitiesIntoDTOs(products);

		return productDTOS;
	}
}
