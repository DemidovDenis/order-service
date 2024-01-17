insert into users (username, password) values ('admin', '$2a$10$ngDgXN7IEshFhBl.XTk2.eJzaiKgTFU1Q9MdpjWPqB7gOm2HOVusu');
insert into users (username, password) values ('user', '$2a$10$rOofS7qX3htNXcPPlxwZ2OVneBsU5nrxFf92lrYlvwsFs08dHkb4K');

insert into roles (name) values ('ROLE_ADMIN'), ('ROLE_USER');

insert into users_roles (user_id, role_id) values (1, 1);
insert into users_roles (user_id, role_id) values (2, 2);

insert into products (name, price) values ('сливочное масло', 100.5);
insert into products (name, price) values ('шашлык', 400.8);
insert into products (name, price) values ('яблоко', 90.3);
insert into products (name, price) values ('сыр', 620.0);
insert into products (name, price) values ('хлеб', 100.5);
insert into products (name, price) values ('цыпленок', 400.8);
insert into products (name, price) values ('арбуз', 90.3);
insert into products (name, price) values ('картофель', 620.0);

insert into orders (date_created, status) values ('2023-11-03', 'PAID');
insert into orders (date_created, status) values ('2023-12-10', 'PAID');
insert into orders (date_created, status) values ('2023-12-11', 'PAID');
insert into orders (date_created, status) values ('2023-12-11', 'PAID');
insert into orders (date_created, status) values ('2023-12-11', 'PAID');
insert into orders (date_created, status) values ('2023-12-11', 'PAID');

insert into order_product (quantity, product_id, order_id) values (10, 1, 1);
insert into order_product (quantity, product_id, order_id) values (11, 2, 2);
insert into order_product (quantity, product_id, order_id) values (15, 3, 3);
insert into order_product (quantity, product_id, order_id) values (25, 4, 4);
insert into order_product (quantity, product_id, order_id) values (5, 5, 5);
insert into order_product (quantity, product_id, order_id) values (8, 6, 6);