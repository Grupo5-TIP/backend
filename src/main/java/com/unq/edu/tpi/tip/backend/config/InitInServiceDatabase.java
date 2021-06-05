
package com.unq.edu.tpi.tip.backend.config;

import com.unq.edu.tpi.tip.backend.exceptions.StateNotFoundException;
import com.unq.edu.tpi.tip.backend.models.*;
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
        productService.createProduct(new Product("Coca Cola","Coca Cola 2 lts", 200.0, "https://bit.ly/2Qx0RML", "Bebidas"));
        productService.createProduct(new Product("Milanesa con fritas","Milanesa de ternera con papas bastón", 650.0, "https://bit.ly/3v3IMov", "Platos"));
        productService.createProduct(new Product("Fideos a la bolognesa","Coca Cola", 450.0, "https://bit.ly/2Q4pSz2", "Platos"));
        productService.createProduct(new Product("Matambre con rusa","Matambre de ternera con ensalada rusa", 400.0, "https://bit.ly/3dqddiw", "Entradas"));
        productService.createProduct(new Product("Tiramisu","Autentico tiramisu italiano", 350.0, "https://bit.ly/3gfRICZ", "Postres"));

        productService.createProduct(new Product("Fanta","Fanta 2 lts", 200.0, "https://bit.ly/3uCxNl3", "Bebidas"));
        productService.createProduct(new Product("Rabas","Rabas fritas doradas", 950.0, "https://bit.ly/3g5C0bK", "Entradas"));
        productService.createProduct(new Product("Asado","Asado de ternera", 1050.0, "https://bit.ly/3c8sxiE", "Platos"));
        productService.createProduct(new Product("Arroz con pollo","Arroz con pollo especiado", 450.0, "https://bit.ly/3i4Y9tw", "Platos"));
        productService.createProduct(new Product("Volcán de chocolate","Postre relleno de chocolate caliente", 350.0, "https://bit.ly/3vLWZXN", "Postres"));

        productService.createProduct(new Product("Sprite","Sprite 2 lts", 200.0, "https://bit.ly/3vUQnXn", "Bebidas"));
        productService.createProduct(new Product("Ensalada Caesar","Lechuga, pollo y croutons con salsa", 650.0, "https://bit.ly/3ib2f36", "Platos"));
        productService.createProduct(new Product("Noquis con salsa","Noquis italianos con salsa", 550.0, "https://bit.ly/3uQEPTH", "Platos"));
        productService.createProduct(new Product("Sopa de zapallo","Sopa a base de calabaza", 400.0, "https://bit.ly/3p9Ij2l", "Entradas"));
        productService.createProduct(new Product("Helado 3 bochas","Frutilla, chocolate y vainilla", 350.0, "https://bit.ly/3vJQLb1", "Postres"));

        productService.createProduct(new Product("Aquarius","Aquarius grande para compartir", 200.0, "https://bit.ly/3g3Mp80", "Bebidas"));
        productService.createProduct(new Product("Empanada de carne","Empanada carne cortada cuchillo", 150.0, "https://bit.ly/3fIKiY0", "Entradas"));
        productService.createProduct(new Product("Pizza mozzarella","Pizza mozzarella con aceitunas", 850.0, "https://bit.ly/3z5lstk", "Platos"));
        productService.createProduct(new Product("Guiso de lentejas","Explosivo guiso de lentejas", 600.0, "https://bit.ly/2TuuXSh", "Platos"));
        productService.createProduct(new Product("Ensalada de frutas","Mix de frutas en cubos", 350.0, "https://bit.ly/3c5Mbfp", "Postres"));
    }

    private void fireInitialDataStates() {
        UsedState used = new UsedState();
        used.setId(1L);
        stateService.save(used);

        AvailableState available = new AvailableState();
        available.setId(2L);
        stateService.save(available);

        RequestBillState billing = new RequestBillState();
        available.setId(3L);
        stateService.save(billing);
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
        OrderTable orderTable5 = new OrderTable(stateService.findById(1L), 450, 330, 4);
        orderTableService.save(orderTable5);
        OrderTable orderTable6 = new OrderTable(stateService.findById(3L), 800, 330, 4);
        orderTableService.save(orderTable6);
    }
}
