package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.exceptions.TableNotFoundException;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.services.OrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController
{
	private final OrderService orderService;

	public OrderController(OrderService orderService){
		this.orderService = orderService;
	}

	@GetMapping(path = "/{tableId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> get(@PathVariable("tableId") Long tableId) throws TableNotFoundException
	{
		OrderDTO orderDTO = orderService.getOrderByTableID(tableId);

		return ResponseEntity.ok(orderDTO);
	}
}
