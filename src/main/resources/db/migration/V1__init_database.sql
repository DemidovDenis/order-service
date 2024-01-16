drop table if exists customers;
drop table if exists products;
drop table if exists orders;
drop table if exists order_product;


create table customers (
    id       bigserial primary key ,
    username varchar (100) not null ,
    password varchar (128) not null ,
    role     varchar (100) not null
);

create table products (
    id    bigserial primary key ,
    name  varchar (255) not null ,
    price double precision not null
    --constraint fk_order_id foreign key (order_id) references orders (id) on delete cascade
);

create table orders (
    id           bigserial primary key ,
    date_created date not null ,
    status       varchar (255) not null
);

create table order_product (
    quantity   serial ,
    product_id bigserial not null ,
    order_id   bigserial not null ,
    constraint fk_product_id foreign key (product_id) references products (id) on delete cascade ,
    constraint fk_order_id foreign key (order_id) references orders (id) on delete cascade ,
    constraint order_product_pkey primary key (product_id,order_id)
);
