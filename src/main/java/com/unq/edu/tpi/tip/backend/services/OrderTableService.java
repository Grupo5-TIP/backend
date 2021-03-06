package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.OrderDoesNotExistException;
import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.mappers.OrderTableMapper;
import com.unq.edu.tpi.tip.backend.models.CustomerOrder;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderTableDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderTableMapper orderTableMapper;
    private final OrderService orderService;

    public OrderTableService(OrderTableRepository orderTableRepository, OrderTableMapper orderTableMapper, OrderService orderService) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableMapper = orderTableMapper;
        this.orderService = orderService;
    }

    public List<OrderTableDTO> getAll() {
        Iterable<OrderTable> tables = this.orderTableRepository.findAll();
        return orderTableMapper.mapEntitiesIntoDTOs(tables);
    }

    public void save(OrderTable orderTable) {
        orderTableRepository.save(orderTable);
    }

    @Transactional(readOnly=true)
    public List<Item> getAllItemsFromTable(Long tableId) throws TableDoesNotExistException
    {

        orderTableRepository.findById(tableId).orElseThrow(
                ()-> new TableDoesNotExistException(tableId));

        List<OrderDTO> orders = orderService.getOrdersByTableID(tableId);
        List<Item> items = new ArrayList<>();

        orders.stream().forEach(order -> {
            order.getOrderedItems().stream().forEach(item -> {
                int index = items.indexOf(item);
                if (index == -1) {
                    items.add(item);
                } else {
                    Item tempItem = items.get(index);
                    tempItem.setAmount(tempItem.getAmount() + item.getAmount());
                }
            });
        });

        return items;
    }


    public void checkBill(Long tableId)
    {
        this.orderService.checkBill(tableId);
    }

    public void requestBill(Long tableId) throws TableDoesNotExistException
    {
        OrderTable orderTable = orderTableRepository.findById(tableId).orElseThrow(() -> new TableDoesNotExistException(tableId));

        orderTable.changeToRequestBillState();
        this.orderTableRepository.save(orderTable);
    }

    public void deleteAllOrdersFromTable(Long tableId) throws TableDoesNotExistException, OrderDoesNotExistException {
        orderTableRepository.findById(tableId).orElseThrow(
                ()-> new TableDoesNotExistException(tableId));

        List<OrderDTO> orders = orderService.getOrdersByTableID(tableId);
        for (OrderDTO order : orders) {
            orderService.deleteOrder(order.getId());
        }
        OrderTable table = orderTableRepository.findById(tableId).get();
        table.setAvailableState();
        orderTableRepository.save(table);
    }

    public List<Item> updateTableOrder(Long tableId, List<Item> items)
            throws TableDoesNotExistException, OrderDoesNotExistException, OrderEmptyException
    {
        orderTableRepository.findById(tableId).orElseThrow(
                ()-> new TableDoesNotExistException(tableId));

        this.deleteAllOrdersFromTable(tableId);

        OrderDTO orderDTO= new OrderDTO();
        orderDTO.setId(0L);
        orderDTO.setTableId(tableId);
        orderDTO.setOrderedItems(items);
        orderService.createOrder(orderDTO);
        return items;
    }
}
