package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.mappers.OrderTableMapper;
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
    private final OrderService customerOrderService;

    public OrderTableService(OrderTableRepository orderTableRepository, OrderTableMapper orderTableMapper, OrderService customerOrderService) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableMapper = orderTableMapper;
        this.customerOrderService = customerOrderService;
    }
    public List<OrderTableDTO> getAll() {
        Iterable<OrderTable> tables = this.orderTableRepository.findAll();
        return orderTableMapper.mapEntitiesIntoDTOs(tables);
    }
    public void save(OrderTable orderTable) {
        orderTableRepository.save(orderTable);
    }


	public List<Item> getAllItemsFromTable(Long tableId) throws TableDoesNotHaveOrdersException
    {
        List<OrderDTO> orderDTOS = customerOrderService.getOrdersByTableID(tableId);
        List<Item> items = new ArrayList<>();

        for (OrderDTO anOrderDto : orderDTOS){
            for(Item anItem : anOrderDto.getOrderedItems()){
                if (items.contains(anItem) ){
                    Item _anItem = items.get(items.indexOf(anItem));
                    _anItem.setAmount(_anItem.getAmount() + anItem.getAmount());
                }else {
                    items.add(anItem);
                }
            }
        }
        return items;
	}
}
