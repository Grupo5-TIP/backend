-- INSERT INTO customer_order (id, table_id) VALUES (1,1), (2,2), (3,3), (4,4);

INSERT INTO product (id, description, name, price, image) VALUES
       (1,'Coca Cola','Coca Cola', 200, 'https://bit.ly/2Qx0RML'),
       (2,'Milanesa de ternera con papas bastón','Milanesa con fritas', 650, 'https://bit.ly/3v3IMov'),
       (3,'Fideos italianos con salsa de tomates','Fideos a la bolognesa', 450, 'https://bit.ly/2Q4pSz2'),
       (4,'Matambre de ternera con ensalada rusa','Matambre con rusa', 400, 'https://bit.ly/3dqddiw'),
       (5,'Autentico tiramisu italiano','Tiramisu', 350, 'https://bit.ly/3gfRICZ');

-- INSERT INTO item (id, amount, product_id) VALUES (1,10,1),(2,2,2),(3,3,3),(4,4,4),(5,1,5);

-- INSERT INTO customer_order_items (order_id, items_id) VALUES (1,1),(2,2),(3,3),(4,4),(4,5);

-- INSERT INTO ordered_item (order_id, item_id) VALUES (1,1),(2,2),(3,3),(4,4),(4,5);

-- INSERT INTO item_orders (orders_id, item_id) VALUES (1,1),(2,2),(3,3),(4,4),(4,5);
