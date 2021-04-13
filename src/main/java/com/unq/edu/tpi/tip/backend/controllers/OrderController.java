package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.exceptions.TableNotFoundException;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
		List<OrderDTO> ordersDTO = orderService.getOrdersByTableID(tableId);

		return ResponseEntity.ok(ordersDTO);
	}

	@PostMapping(path = "", produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createOrder( @RequestBody OrderDTO orderDTO){

		OrderDTO createdOrder = orderService.createOrder(orderDTO);
		return new ResponseEntity<OrderDTO>(createdOrder,HttpStatus.CREATED);
	}
}
