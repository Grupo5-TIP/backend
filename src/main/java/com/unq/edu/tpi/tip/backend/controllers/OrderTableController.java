package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.aspects.ExceptionAspect;
import com.unq.edu.tpi.tip.backend.exceptions.OrderDoesNotExistException;
import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderTableDTO;
import com.unq.edu.tpi.tip.backend.services.OrderTableService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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
    public ResponseEntity<?> getTableById(@PathVariable("tableId") Long tableId)
            throws TableDoesNotExistException {
        return ResponseEntity.ok(orderTableService.getTableById(tableId));
    }

    @ExceptionAspect
    @GetMapping(path = "/items/{tableId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllItemsFromTable(@PathVariable("tableId") Long tableId)
            throws TableDoesNotExistException
    {
        List<Item> items = orderTableService.getAllItemsFromTable(tableId);

        return ResponseEntity.ok(items);
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
        return ResponseEntity.ok().build();
    }

    @ExceptionAspect
    @PutMapping(path = "/{tableId}", produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTableOrder(@PathVariable("tableId") Long tableId, @RequestBody List<Item> items)
            throws TableDoesNotExistException, OrderDoesNotExistException, OrderEmptyException
    {
        List<Item> itemUpdated = orderTableService.updateTableOrder(tableId, items);
        return ResponseEntity.ok(itemUpdated);
    }

    @ExceptionAspect
    @PutMapping(path = "/{tableId}/mpstate", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> changeStateToMercadoPago(@PathVariable("tableId") Long tableId)
            throws TableDoesNotExistException
    {
        orderTableService.changeStateToMercadoPago(tableId);
        return ResponseEntity.ok().build();
    }

}
