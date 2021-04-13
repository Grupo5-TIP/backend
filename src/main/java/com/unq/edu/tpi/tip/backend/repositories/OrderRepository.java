package com.unq.edu.tpi.tip.backend.repositories;

import com.unq.edu.tpi.tip.backend.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	Optional<List<Order>> findAllByTableId(Long tableId);
}
