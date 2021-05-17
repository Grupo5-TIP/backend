package com.unq.edu.tpi.tip.backend.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO
{
	private Long id;
	private String name;
	private String description;
	private Double price;
	private String image;
	private String category;
}
