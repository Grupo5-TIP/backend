package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.mappers.OrderTableMapper;
import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.models.State;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderTableDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderTableMapper orderTableMapper;

    public OrderTableService(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableMapper = new OrderTableMapper();
    }
    public List<OrderTableDTO> getAll() {
        Iterable<OrderTable> tables = this.orderTableRepository.findAll();
        return orderTableMapper.mapEntitiesIntoDTOs(tables);
    }
    public void save(OrderTable orderTable) {
        orderTableRepository.save(orderTable);
    }
}
