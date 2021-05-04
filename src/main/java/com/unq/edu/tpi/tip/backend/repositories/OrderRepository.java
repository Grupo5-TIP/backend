package com.unq.edu.tpi.tip.backend.repositories;

import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long>
{
	@EntityGraph(value = "orderedItems", attributePaths= {"orderedItems"})
	Optional<List<CustomerOrder>> findAllByTableId(Long tableId);
	@EntityGraph(value = "orderedItems", attributePaths= {"orderedItems"})
	List<CustomerOrder> findAll();
}
