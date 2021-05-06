
package com.unq.edu.tpi.tip.backend.config;

import com.unq.edu.tpi.tip.backend.exceptions.StateNotFoundException;
import com.unq.edu.tpi.tip.backend.models.AvailableState;
import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.models.Product;
import com.unq.edu.tpi.tip.backend.models.UsedState;
import com.unq.edu.tpi.tip.backend.services.OrderTableService;
import com.unq.edu.tpi.tip.backend.services.ProductService;
import com.unq.edu.tpi.tip.backend.services.StateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitInServiceDatabase {
    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    private final OrderTableService orderTableService;

    private final ProductService productService;

    private final StateService stateService;

    public InitInServiceDatabase(OrderTableService orderTableService,ProductService productService, StateService stateService){
        this.orderTableService = orderTableService;
        this.productService = productService;
        this.stateService = stateService;
    }

    @PostConstruct
    public void initialize() throws Exception {
        if (className.equals("com.mysql.cj.jdbc.Driver")) {
            fireInitialDataProducts();
            fireInitialDataStates();
            fireInitialDataOrderTable();
        }
    }

    private void fireInitialDataProducts() throws Exception {
        productService.createProduct(new Product("Coca Cola","Coca Cola 2 lts", 200.0, "https://bit.ly/2Qx0RML"));
        productService.createProduct(new Product("Milanesa con fritas","Milanesa de ternera con papas bastón", 650.0, "https://bit.ly/3v3IMov"));
        productService.createProduct(new Product("Fideos a la bolognesa","Coca Cola", 450.0, "https://bit.ly/2Q4pSz2"));
        productService.createProduct(new Product("Matambre con rusa","Matambre de ternera con ensalada rusa", 400.0, "https://bit.ly/3dqddiw"));
        productService.createProduct(new Product("Tiramisu","Autentico tiramisu italiano", 350.0, "https://bit.ly/3gfRICZ"));
    }

    private void fireInitialDataStates() {
        UsedState used = new UsedState();
        used.setId(1L);
        stateService.save(used);

        AvailableState available = new AvailableState();
        available.setId(2L);
        stateService.save(available);
    }

    private void fireInitialDataOrderTable() throws StateNotFoundException {
        OrderTable orderTable1 = new OrderTable(stateService.findById(1L), 1, 10, 4);
        orderTableService.save(orderTable1);
        OrderTable orderTable2 = new OrderTable(stateService.findById(2L), 450, 10, 2);
        orderTableService.save(orderTable2);
        OrderTable orderTable3 = new OrderTable(stateService.findById(2L), 900, 100, 2);
        orderTableService.save(orderTable3);
        OrderTable orderTable4 = new OrderTable(stateService.findById(1L), 0, 330, 6);
        orderTableService.save(orderTable4);

    }
}
