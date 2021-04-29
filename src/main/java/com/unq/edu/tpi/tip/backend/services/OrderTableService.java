package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.mappers.OrderTableMapper;
import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderTableDTO;
import com.unq.edu.tpi.tip.backend.repositories.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderTableService {
    private OrderTableRepository orderTableRepository;
    private OrderTableMapper orderTableMapper;

    public List<OrderTableDTO> getAll() {
        Iterable<OrderTable> tables = this.orderTableRepository.findAll();
        return orderTableMapper.mapEntitiesIntoDTOs(tables);
    }
}
