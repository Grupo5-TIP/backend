package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.aspects.ExceptionAspect;
import com.unq.edu.tpi.tip.backend.mappers.ProductMapper;
import com.unq.edu.tpi.tip.backend.models.Product;
import com.unq.edu.tpi.tip.backend.models.dtos.ProductCategoryDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.ProductDTO;
import com.unq.edu.tpi.tip.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService
{
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	public ProductService(ProductRepository productRepository, ProductMapper productMapper){
		this.productRepository = productRepository;
		this.productMapper = productMapper;
	}

	/*public static void main(String[] args)
	{
		List<ProductDTO>  prods = getAll();
	}
	*/
	//public List<ProductDTO> getAll()
	//public static List<ProductDTO> getAll()
	public HashMap<String, List<ProductDTO>> getAll()
	{
		Iterable<Product> products = this.productRepository.findAll();
		List<ProductDTO> productDTOS = this.productMapper.mapEntitiesIntoDTOs(products);
		//List<ProductDTO> productDTOS = createProds();

		HashMap<String, List<ProductDTO>> productCategoryDTOS = new HashMap();

		for (ProductDTO aProductDTO : productDTOS){
			if (productCategoryDTOS.containsKey( aProductDTO.getCategory())){
				List<ProductDTO> prodSet = productCategoryDTOS.get(aProductDTO.getCategory());
				List<ProductDTO> prodSet2 = prodSet.stream().collect(Collectors.toList());
				prodSet2.add(aProductDTO);
				productCategoryDTOS.put(aProductDTO.getCategory(), prodSet2);
			}else{
				productCategoryDTOS.put(aProductDTO.getCategory(), Arrays.asList(aProductDTO));
			}
		}

		//return productDTOS;
		return productCategoryDTOS;
	}

	private static List<ProductDTO> createProds()
	{
		List<ProductDTO> prods = new ArrayList<>();
		ProductDTO prod = new ProductDTO();
		prod.setName("test");
		prod.setCategory("bebidas");

		prods.add(prod);

		prod = new ProductDTO();
		prod.setName("test2");
		prod.setCategory("bebidas");

		prods.add(prod);

		prod = new ProductDTO();
		prod.setName("test3");
		prod.setCategory("platos");

		prods.add(prod);

		return prods;
	}

	public Product createProduct(Product product) {
		Product newProduct = new Product(product.getName(),
				product.getDescription(),
				product.getPrice(),
				product.getImage(),
				product.getCategory());

		productRepository.save(newProduct);
		return newProduct;
	}
}
