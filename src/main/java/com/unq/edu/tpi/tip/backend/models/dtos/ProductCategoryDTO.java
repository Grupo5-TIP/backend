package com.unq.edu.tpi.tip.backend.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryDTO{
	private String category;
	private List<ProductDTO> productsDTO = new ArrayList<>();
}
