package ru.demidov.orderservice.repository;

import ru.demidov.orderservice.entity.OrderProduct;

public interface OrderProductRepository {
    OrderProduct save(OrderProduct orderProduct);
    OrderProduct update(OrderProduct orderProduct);
}
