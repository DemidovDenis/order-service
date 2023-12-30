drop table if exists users;
drop table if exists products;
drop table if exists orders;


create table users (
    id       bigserial primary key ,
    username varchar (100) not null ,
    email    varchar(100) not null unique ,
    password varchar (128) not null
);

create table products (
    id          bigserial primary key ,
    name        varchar (255) not null ,
    description varchar (255) not null ,
    price       double precision not null
);

create table orders (
    id            bigserial primary key ,
    enabled_order boolean not null default false ,
    created       timestamp not null ,
    product_id    bigserial not null ,
    constraint fk_products_id foreign key (product_id) references products (id) on delete cascade
);
