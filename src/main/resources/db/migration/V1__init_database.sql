drop table if exists users;
drop table if exists roles;
drop table if exists users_roles;
drop table if exists products;
drop table if exists orders;
drop table if exists order_product;


create table users (
    id       bigserial primary key ,
    username varchar (100) not null ,
    password varchar (128) not null
);

create table roles (
    id   bigserial primary key ,
    name varchar (100) not null
);

create table users_roles (
    user_id bigserial ,
    role_id bigserial ,
    constraint fk_user_id foreign key (user_id) references users (id) on delete cascade ,
    constraint fk_role_id foreign key (role_id) references roles (id) on delete cascade ,
    constraint user_role_pk primary key (user_id, role_id)
);

create table products (
    id    bigserial primary key ,
    name  varchar (255) not null ,
    price double precision not null
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
