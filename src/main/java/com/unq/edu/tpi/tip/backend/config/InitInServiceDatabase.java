
package com.unq.edu.tpi.tip.backend.config;

import com.unq.edu.tpi.tip.backend.exceptions.InvoiceDoesNotHaveOrdersException;
import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.StateNotFoundException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotExistException;
import com.unq.edu.tpi.tip.backend.mappers.ProductMapper;
import com.unq.edu.tpi.tip.backend.models.*;
import com.unq.edu.tpi.tip.backend.models.dtos.InvoiceDTO;
import com.unq.edu.tpi.tip.backend.models.dtos.OrderDTO;
import com.unq.edu.tpi.tip.backend.repositories.UserRepository;
import com.unq.edu.tpi.tip.backend.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitInServiceDatabase {
    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;
    private final OrderTableService orderTableService;
    private final ProductService productService;
    private final StateService stateService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final UserRepository userRepository;

    public InitInServiceDatabase(
            OrderTableService orderTableService,
            ProductService productService,
            StateService stateService,
            UserRepository userRepository,
            OrderService orderService,
            InvoiceService invoiceService){

        this.orderTableService = orderTableService;
        this.productService = productService;
        this.stateService = stateService;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

    @PostConstruct
    public void initialize() throws Exception {
        if (className.equals("com.mysql.cj.jdbc.Driver")) {
            fireInitialDataProducts();
            fireInitialDataStates();
            fireInitialDataOrderTable();
            fireInitialDataUsers();
            fireInitialHistoricInvoicesData();
        }
    }

    private void fireInitialDataUsers()
    {
        User admin = new User("admin", "MXEydzNlNHI=", true);
        userRepository.save(admin);

        User cajero = new User("cajero", "MXEydzNlNHI=", false);
        userRepository.save(cajero);

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

        MercadoPagoState mercadoPagoState = new MercadoPagoState();
        available.setId(4L);
        stateService.save(mercadoPagoState);
    }

    private void fireInitialDataOrderTable() throws StateNotFoundException {
        OrderTable orderTable1 = new OrderTable(stateService.findById(1L), 1, 10, 4);
        orderTable1.setId(1L);
        orderTableService.save(orderTable1);
        OrderTable orderTable2 = new OrderTable(stateService.findById(2L), 450, 10, 2);
        orderTable2.setId(2L);
        orderTableService.save(orderTable2);
        OrderTable orderTable3 = new OrderTable(stateService.findById(2L), 900, 100, 2);
        orderTable1.setId(3L);
        orderTableService.save(orderTable3);
        OrderTable orderTable4 = new OrderTable(stateService.findById(1L), 0, 330, 6);
        orderTable1.setId(4L);
        orderTableService.save(orderTable4);
        OrderTable orderTable5 = new OrderTable(stateService.findById(1L), 450, 330, 4);
        orderTable1.setId(5L);
        orderTableService.save(orderTable5);
        OrderTable orderTable6 = new OrderTable(stateService.findById(3L), 800, 330, 4);
        orderTable1.setId(6L);
        orderTableService.save(orderTable6);
    }

    private void fireInitialHistoricInvoicesData() throws TableDoesNotExistException, InvoiceDoesNotHaveOrdersException, OrderEmptyException {
        InvoiceDTO invoiceEF = new InvoiceDTO("EF");
        InvoiceDTO invoiceMP = new InvoiceDTO("MP");

        ProductMapper mapper = new ProductMapper();

        List<Product> platos = mapper.mapToPojos(productService.getAll().get("Platos"));
        Item plato001 = new Item(5, platos.get(0));
        Item plato002 = new Item(5, platos.get(1));
        Item plato003 = new Item(5, platos.get(2));
        Item plato004 = new Item(5, platos.get(3));
        Item plato005 = new Item(5, platos.get(4));

        platos = mapper.mapToPojos(productService.getAll().get("Platos"));
        Item plato006 = new Item(5, platos.get(0));
        Item plato007 = new Item(5, platos.get(1));
        Item plato008 = new Item(5, platos.get(2));
        Item plato009 = new Item(5, platos.get(3));
        Item plato010 = new Item(5, platos.get(4));

        List<Product> bebidas = mapper.mapToPojos(productService.getAll().get("Bebidas"));
        Item bebida001 = new Item(5, bebidas.get(0));
        Item bebida002 = new Item(5, bebidas.get(1));
        Item bebida003 = new Item(5, bebidas.get(2));
        Item bebida004 = new Item(5, bebidas.get(3));

        bebidas = mapper.mapToPojos(productService.getAll().get("Bebidas"));
        Item bebida005 = new Item(5, bebidas.get(0));
        Item bebida006 = new Item(5, bebidas.get(1));
        Item bebida007 = new Item(5, bebidas.get(2));
        Item bebida008 = new Item(5, bebidas.get(3));

        bebidas = mapper.mapToPojos(productService.getAll().get("Bebidas"));
        Item bebida009 = new Item(5, bebidas.get(0));
        Item bebida010 = new Item(5, bebidas.get(1));
        Item bebida011 = new Item(5, bebidas.get(2));
        Item bebida012 = new Item(5, bebidas.get(3));

        List<Product> postres = mapper.mapToPojos(productService.getAll().get("Postres"));
        Item postre001 = new Item(5, postres.get(0));
        Item postre002 = new Item(5, postres.get(1));
        Item postre003 = new Item(5, postres.get(2));
        Item postre004 = new Item(5, postres.get(3));

        postres = mapper.mapToPojos(productService.getAll().get("Postres"));
        Item postre005 = new Item(5, postres.get(0));
        Item postre006 = new Item(5, postres.get(1));
        Item postre007 = new Item(5, postres.get(2));
        Item postre008 = new Item(5, postres.get(3));

        List<Product> entradas = mapper.mapToPojos(productService.getAll().get("Entradas"));
        Item entrada001 = new Item(5, entradas.get(0));
        Item entrada002 = new Item(5, entradas.get(1));
        Item entrada003 = new Item(5, entradas.get(2));
        Item entrada004 = new Item(5, entradas.get(3));

        entradas = mapper.mapToPojos(productService.getAll().get("Entradas"));
        Item entrada005 = new Item(5, entradas.get(0));
        Item entrada006 = new Item(5, entradas.get(1));
        Item entrada007 = new Item(5, entradas.get(2));
        Item entrada008 = new Item(5, entradas.get(3));

        List<Item> items001 = new ArrayList<>();
        items001.add(plato001);

        List<Item> items002 = new ArrayList<>();
        items002.add(plato002);

        List<Item> items003 = new ArrayList<>();
        items003.add(plato003);

        List<Item> items004 = new ArrayList<>();
        items004.add(plato004);

        List<Item> items005 = new ArrayList<>();
        items005.add(plato005);

        List<Item> items006 = new ArrayList<>();
        items006.add(plato006);

        List<Item> items007 = new ArrayList<>();
        items007.add(plato007);

        List<Item> items008 = new ArrayList<>();
        items008.add(plato008);

        List<Item> items009 = new ArrayList<>();
        items009.add(plato009);

        List<Item> items010 = new ArrayList<>();
        items010.add(plato010);

        List<Item> items011 = new ArrayList<>();
        items011.add(entrada001);

        List<Item> items012 = new ArrayList<>();
        items012.add(entrada002);

        List<Item> items013 = new ArrayList<>();
        items013.add(entrada003);

        List<Item> items014 = new ArrayList<>();
        items014.add(entrada004);

        List<Item> items015 = new ArrayList<>();
        items015.add(entrada005);

        List<Item> items016 = new ArrayList<>();
        items016.add(entrada006);

        List<Item> items017 = new ArrayList<>();
        items017.add(entrada007);

        List<Item> items018 = new ArrayList<>();
        items018.add(entrada008);

        List<Item> items019 = new ArrayList<>();
        items019.add(bebida001);

        List<Item> items020 = new ArrayList<>();
        items020.add(bebida002);

        List<Item> items021 = new ArrayList<>();
        items021.add(bebida003);

        List<Item> items022 = new ArrayList<>();
        items022.add(bebida004);

        List<Item> items023 = new ArrayList<>();
        items023.add(bebida005);

        List<Item> items024 = new ArrayList<>();
        items024.add(bebida006);

        List<Item> items026 = new ArrayList<>();
        items026.add(bebida007);

        List<Item> items027 = new ArrayList<>();
        items027.add(bebida008);

        List<Item> items028 = new ArrayList<>();
        items028.add(bebida009);

        List<Item> items029 = new ArrayList<>();
        items029.add(bebida010);

        List<Item> items030 = new ArrayList<>();
        items030.add(bebida011);

        List<Item> items031 = new ArrayList<>();
        items031.add(bebida012);

        List<Item> items032 = new ArrayList<>();
        items032.add(postre001);

        List<Item> items033 = new ArrayList<>();
        items033.add(postre002);

        List<Item> items034 = new ArrayList<>();
        items034.add(postre003);

        List<Item> items035 = new ArrayList<>();
        items035.add(postre004);

        List<Item> items036 = new ArrayList<>();
        items036.add(postre005);

        List<Item> items037 = new ArrayList<>();
        items037.add(postre006);

        List<Item> items038 = new ArrayList<>();
        items038.add(postre007);

        List<Item> items039 = new ArrayList<>();
        items039.add(postre008);

        OrderDTO order001 = new OrderDTO(1L, items001);

        orderService.createOrder(order001);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,1,1,12,0));

        OrderDTO order002 = new OrderDTO(4L, items002);

        orderService.createOrder(order002);
        invoiceService.createInvoice(4L, invoiceMP, LocalDateTime.of(2021,2,2,12,0));

        OrderDTO order003 = new OrderDTO(2L, items003);

        orderService.createOrder(order003);
        invoiceService.createInvoice(2L, invoiceEF, LocalDateTime.of(2021,2,15,12,0));

        OrderDTO order004 = new OrderDTO(3L, items004);

        orderService.createOrder(order004);
        invoiceService.createInvoice(3L, invoiceMP, LocalDateTime.of(2021,2,2,12,0));

        OrderDTO order005 = new OrderDTO(1L, items005);

        orderService.createOrder(order005);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,2,2,12,0));

        OrderDTO order006 = new OrderDTO(1L, items006);

        orderService.createOrder(order006);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,2,20,12,0));

        OrderDTO order007 = new OrderDTO(1L, items007);

        orderService.createOrder(order007);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,2,21,12,0));

        OrderDTO order008 = new OrderDTO(1L, items008);

        orderService.createOrder(order008);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,4,12,0));

        OrderDTO order009 = new OrderDTO(1L, items009);

        orderService.createOrder(order009);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,18,12,0));

        OrderDTO order010 = new OrderDTO(1L, items010);

        orderService.createOrder(order010);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,20,12,0));

        OrderDTO order011 = new OrderDTO(1L, items011);

        orderService.createOrder(order011);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,2,12,0));

        OrderDTO order012 = new OrderDTO(1L, items012);

        orderService.createOrder(order012);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,2,12,0));

        OrderDTO order013 = new OrderDTO(1L, items013);

        orderService.createOrder(order013);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,2,12,0));

        OrderDTO order014 = new OrderDTO(1L, items014);

        orderService.createOrder(order014);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,2,12,0));

        OrderDTO order015 = new OrderDTO(1L, items015);

        orderService.createOrder(order015);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,2,12,0));

        OrderDTO order016 = new OrderDTO(1L, items016);

        orderService.createOrder(order016);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,2,12,0));

        OrderDTO order017 = new OrderDTO(1L, items017);

        orderService.createOrder(order017);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,2,12,0));

        OrderDTO order018 = new OrderDTO(1L, items018);

        orderService.createOrder(order018);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,2,12,0));

        OrderDTO order019 = new OrderDTO(1L, items019);

        orderService.createOrder(order019);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,3,2,12,0));

        OrderDTO order020 = new OrderDTO(1L, items020);

        orderService.createOrder(order020);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,4,2,12,0));

        OrderDTO order021 = new OrderDTO(1L, items021);

        orderService.createOrder(order021);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,4,2,12,0));

        OrderDTO order022 = new OrderDTO(1L, items022);

        orderService.createOrder(order022);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,4,2,12,0));

        OrderDTO order023 = new OrderDTO(1L, items023);

        orderService.createOrder(order023);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,4,2,12,0));

        OrderDTO order024 = new OrderDTO(1L, items024);

        orderService.createOrder(order024);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,4,2,12,0));

        OrderDTO order026 = new OrderDTO(1L, items026);

        orderService.createOrder(order026);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,4,2,12,0));

        OrderDTO order027 = new OrderDTO(1L, items027);

        orderService.createOrder(order027);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,4,2,12,0));

        OrderDTO order028 = new OrderDTO(1L, items028);

        orderService.createOrder(order028);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,4,2,12,0));

        OrderDTO order029 = new OrderDTO(1L, items029);

        orderService.createOrder(order029);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,5,2,12,0));

        OrderDTO order030 = new OrderDTO(1L, items030);

        orderService.createOrder(order030);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,5,2,12,0));

        OrderDTO order031 = new OrderDTO(1L, items031);

        orderService.createOrder(order031);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,5,2,12,0));

        OrderDTO order032 = new OrderDTO(1L, items032);

        orderService.createOrder(order032);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,5,2,12,0));

        OrderDTO order033 = new OrderDTO(1L, items033);

        orderService.createOrder(order033);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,5,2,12,0));

        OrderDTO order034 = new OrderDTO(1L, items034);

        orderService.createOrder(order034);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,6,2,12,0));

        OrderDTO order035 = new OrderDTO(1L, items035);

        orderService.createOrder(order035);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,6,2,12,0));

        OrderDTO order036 = new OrderDTO(1L, items036);

        orderService.createOrder(order036);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,6,2,12,0));

        OrderDTO order037 = new OrderDTO(1L, items037);

        orderService.createOrder(order037);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,6,2,12,0));

        OrderDTO order038 = new OrderDTO(1L, items038);

        orderService.createOrder(order038);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,6,2,12,0));

        OrderDTO order039 = new OrderDTO(1L, items039);

        orderService.createOrder(order039);
        invoiceService.createInvoice(1L, invoiceEF, LocalDateTime.of(2021,6,2,12,0));

    }
}
