package com.unq.edu.tpi.tip.backend.mappers;

import com.unq.edu.tpi.tip.backend.models.Product;
import com.unq.edu.tpi.tip.backend.models.dtos.ProductDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper
{
	public List<ProductDTO> mapEntitiesIntoDTOs(Iterable<Product> entities) {
		List<ProductDTO> dtos = new ArrayList<>();

		entities.forEach(e -> dtos.add(this.mapEntityIntoDTO(e)));

		return dtos;
	}

	public ProductDTO mapEntityIntoDTO(Product product)
	{
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		productDTO.setId(product.getId());
		productDTO.setImage(product.getImage());
		return productDTO;
	}

}
