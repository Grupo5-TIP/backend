package com.unq.edu.tpi.tip.backend.repositories;

import com.unq.edu.tpi.tip.backend.models.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>
{
	@EntityGraph(value = "items", attributePaths= {"items"})
	List<Product> findAll();
}
