package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.aspects.ExceptionAspect;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotHaveOrdersException;
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
    public ResponseEntity<?> getAll() throws TableDoesNotHaveOrdersException
    {
        List<OrderTableDTO> tablesDTO = orderTableService.getAll();

        return ResponseEntity.ok(tablesDTO);
    }

    @ExceptionAspect
    @GetMapping(path = "/{tableId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllItemsFromTable(@PathVariable("tableId") Long tableId)
            throws TableDoesNotHaveOrdersException
    {

        List<Item> items = orderTableService.getAllItemsFromTable(tableId);

        return ResponseEntity.ok(items);
    }
}
