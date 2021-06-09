package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.InvoiceDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
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

    public InvoiceDTO createInvoice(Long tableId, InvoiceDTO invoiceDTO)
            throws TableDoesNotExistException, InvoiceDoesNotHaveOrdersException
    {
        List<OrderDTO> orders = orderService.getOrdersByTableID(tableId);
        Invoice tempInvoice = new Invoice();
        if (!orders.isEmpty()){
            tempInvoice.setPaymentType(invoiceDTO.getPaymentType());
            tempInvoice.setCustomerOrder(orderMapper.mapDTOSIntoEntities(orders));
        }else{
            throw new InvoiceDoesNotHaveOrdersException();
        }

        orderService.checkBill(tableId);

        return invoiceMapper.mapEntityIntoDTO(
                invoiceRepository.save(tempInvoice));

    }
}
