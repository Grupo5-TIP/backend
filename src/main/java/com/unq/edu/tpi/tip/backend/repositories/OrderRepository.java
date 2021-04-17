package com.unq.edu.tpi.tip.backend.repositories;

import com.unq.edu.tpi.tip.backend.models.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
	@EntityGraph(value = "orderedItems", attributePaths= {"orderedItems"})
	Optional<List<Order>> findAllByTableId(Long tableId);
	@EntityGraph(value = "orderedItems", attributePaths= {"orderedItems"})
	List<Order> findAll();
}
