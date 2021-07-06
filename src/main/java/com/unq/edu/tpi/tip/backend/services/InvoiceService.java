package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.InvoiceDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.mappers.InvoiceMapper;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.mappers.OrderTableMapper;
import com.unq.edu.tpi.tip.backend.models.Invoice;
import com.unq.edu.tpi.tip.backend.models.Item;
import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceByMonthDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.InvoiceRepository;
import com.unq.edu.tpi.tip.backend.repositories.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public InvoiceDTO createInvoice(Long tableId, InvoiceDTO invoiceDTO, LocalDateTime date)
            throws TableDoesNotExistException, InvoiceDoesNotHaveOrdersException
    {
        List<OrderDTO> orders = orderService.getOrdersByTableID(tableId);
        Invoice tempInvoice = new Invoice();

        if (!orders.isEmpty()){
            Double invoiceAmmount = orders.stream().mapToDouble(
                    (ord) -> ord.getOrderedItems().stream().mapToDouble(
                            (item) -> (item.getAmount() * item.getProduct().getPrice())).sum()).sum();

            tempInvoice.setPaymentType(invoiceDTO.getPaymentType());
            tempInvoice.setCustomerOrder(orderMapper.mapDTOSIntoEntities(orders));
            tempInvoice.setDate(date);
            tempInvoice.setAmmount(invoiceAmmount);
        }else{
            throw new InvoiceDoesNotHaveOrdersException();
        }

        orderService.checkBill(tableId);

        return invoiceMapper.mapEntityIntoDTO(
                invoiceRepository.save(tempInvoice));

    }

    public List<InvoiceByMonthDTO> getInvoicesByMonth() {
        List<InvoiceByMonthDTO> monthlyInvoices = new ArrayList<>();
        List<InvoiceByMonthDTO> tempInvoices = null;
        List<Invoice> invoices = invoiceRepository.findAll();

        LocalDate date = LocalDate.now();

        for(Invoice invoice : invoices) {
            tempInvoices = monthlyInvoices.stream()
                    .filter((monthInv) -> (
                            monthInv.getMonth().equals(invoice.getDate().getMonth().name()) &&
                            monthInv.getYear().equals(date.getYear())))
                    .collect(Collectors.toList());

            if (tempInvoices.size() > 0) {
                tempInvoices.get(0).setTotalAmmount(
                        tempInvoices.get(0).getTotalAmmount() + invoice.getAmmount());
            } else {
                InvoiceByMonthDTO tempMonthDTO = new InvoiceByMonthDTO(
                        invoice.getDate().getYear(),
                        invoice.getDate().getMonth().name());
                tempMonthDTO.setTotalAmmount(invoice.getAmmount());

                monthlyInvoices.add(tempMonthDTO);
            }
        }

        return monthlyInvoices;
    }
}
