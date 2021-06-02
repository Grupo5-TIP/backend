package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.aspects.ExceptionAspect;
import com.unq.edu.tpi.tip.backend.exceptions.OrderDoesNotExistException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderTableDTO;
import com.unq.edu.tpi.tip.backend.services.OrderTableService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
@CrossOrigin(origins = "*")
public class OrderTableController {
    private final OrderTableService orderTableService;

    public OrderTableController(OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }
    
    @ExceptionAspect
    @GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAll()
    {
        List<OrderTableDTO> tablesDTO = orderTableService.getAll();

        return ResponseEntity.ok(tablesDTO);
    }

    @ExceptionAspect
    @GetMapping(path = "/{tableId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllItemsFromTable(@PathVariable("tableId") Long tableId)
            throws TableDoesNotExistException
    {
        List<Item> items = orderTableService.getAllItemsFromTable(tableId);

        return ResponseEntity.ok(items);
    }

    @ExceptionAspect
    @GetMapping(path = "/check/{tableId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> checkBill(@PathVariable("tableId") Long tableId)
    {

        orderTableService.checkBill(tableId);

        return ResponseEntity.ok("");
    }

    @ExceptionAspect
    @GetMapping(path = "/request/{tableId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> requestBill(@PathVariable("tableId") Long tableId)
            throws TableDoesNotExistException
    {
        orderTableService.requestBill(tableId);

        return ResponseEntity.ok("");
    }

    @ExceptionAspect
    @DeleteMapping(path = "/{tableId}")
    public ResponseEntity<?> deleteAllOrdersFromTable(@PathVariable("tableId") Long tableId)
            throws TableDoesNotExistException, OrderDoesNotExistException {
        orderTableService.deleteAllOrdersFromTable(tableId);
        return ResponseEntity.accepted().build();
    }

}
