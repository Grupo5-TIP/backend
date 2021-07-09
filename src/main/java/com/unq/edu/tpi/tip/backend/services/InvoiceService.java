package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.InvoiceDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.mappers.InvoiceMapper;
import com.unq.edu.tpi.tip.backend.mappers.OrderMapper;
import com.unq.edu.tpi.tip.backend.models.Invoice;
import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceByMonthDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    public List<InvoiceByMonthDTO> getInvoicesByMonth() throws ParseException
    {
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
                        invoice.getDate().getMonth().name(),
                        invoice.getDate().getMonthValue());
                tempMonthDTO.setTotalAmmount(invoice.getAmmount());

                monthlyInvoices.add(tempMonthDTO);
            }
        }


        return orderByMonth(monthlyInvoices);
    }

    private List<InvoiceByMonthDTO> orderByMonth(List<InvoiceByMonthDTO> monthlyInvoices) throws ParseException
    {
        InvoiceByMonthDTO[] invoiceByMonthDTOS = new InvoiceByMonthDTO[12];
        for (InvoiceByMonthDTO invoiceByMonthDTO : monthlyInvoices){
            invoiceByMonthDTOS[invoiceByMonthDTO.getMonthNumber()-1] = invoiceByMonthDTO;
        }
        return Arrays.asList(invoiceByMonthDTOS)
                .stream().filter((e)->e != null)
                .collect(Collectors.toList());
    }
}
