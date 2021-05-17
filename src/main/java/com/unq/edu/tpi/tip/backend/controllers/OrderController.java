package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.aspects.ExceptionAspect;
import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController
{
	private final OrderService orderService;

	public OrderController(OrderService orderService){
		this.orderService = orderService;
	}

	@ExceptionAspect
	@GetMapping(path = "/{tableId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> get(@PathVariable("tableId") Long tableId) throws TableDoesNotHaveOrdersException
	{
		List<OrderDTO> ordersDTO = orderService.getOrdersByTableID(tableId);

		return ResponseEntity.ok(ordersDTO);
	}

	@ExceptionAspect
	@GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getAll() throws TableDoesNotHaveOrdersException
	{
		List<OrderDTO> ordersDTO = orderService.getAll();

		return ResponseEntity.ok(ordersDTO);
	}


	@ExceptionAspect
	@PostMapping(path = "", produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createOrder( @RequestBody OrderDTO orderDTO) throws OrderEmptyException
	{

		OrderDTO createdOrder = orderService.createOrder(orderDTO);
		return new ResponseEntity<OrderDTO>(createdOrder,HttpStatus.CREATED);
	}


}
