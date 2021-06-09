package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.mappers.InvoiceMapper;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.mappers.OrderTableMapper;
import com.unq.edu.tpi.tip.backend.models.Invoice;
import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.InvoiceRepository;
import com.unq.edu.tpi.tip.backend.repositories.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          InvoiceMapper invoiceMapper,
                          OrderMapper orderMapper,
                          OrderService orderService) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    public InvoiceDTO createInvoice(Long tableId, InvoiceDTO invoiceDTO) throws TableDoesNotExistException {
        List<OrderDTO> orders = orderService.getOrdersByTableID(tableId);

        Invoice tempInvoice = new Invoice();
        tempInvoice.setPaymentType(invoiceDTO.getPaymentType());
        tempInvoice.setCustomerOrder(orderMapper.mapDTOSIntoEntities(orders));

        return invoiceMapper.mapEntityIntoDTO(
                invoiceRepository.save(tempInvoice));
    }
}
