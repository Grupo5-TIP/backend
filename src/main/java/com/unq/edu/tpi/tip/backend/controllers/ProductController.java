package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.aspects.ExceptionAspect;
import com.unq.edu.tpi.tip.backend.models.dtos.ProductDTO;
import com.unq.edu.tpi.tip.backend.services.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController
{
	private final ProductService productService;

	public ProductController(ProductService productService){
		this.productService = productService;
	}

	@GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ExceptionAspect
	public ResponseEntity<?> getAll()
	{
		Map<String, List<ProductDTO>> productsDTO = productService.getAll();

		return ResponseEntity.ok(productsDTO);
	}
}
