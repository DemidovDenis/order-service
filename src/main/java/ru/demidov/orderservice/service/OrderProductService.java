package ru.demidov.orderservice.service;

import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.entity.OrderProduct;
import ru.demidov.orderservice.entity.Product;

public interface OrderProductService {

    OrderProduct create(OrderProduct orderProduct);
    OrderProduct update(Order order, Product product, Integer quantity);
}
