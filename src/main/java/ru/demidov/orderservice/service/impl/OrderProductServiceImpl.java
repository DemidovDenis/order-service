package ru.demidov.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.entity.OrderProduct;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.repository.impl.OrderProductRepositoryImpl;
import ru.demidov.orderservice.service.OrderProductService;

@Slf4j
@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService {

    private OrderProductRepositoryImpl orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepositoryImpl orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public OrderProduct create(OrderProduct orderProduct) {
        log.debug("save {}", orderProduct);
        return orderProductRepository.save(orderProduct);
    }

    @Override
    public OrderProduct update(Order updateOrder, Product updateProduct, Integer updateQuantity) {
        OrderProduct orderProduct = new OrderProduct(updateOrder,updateProduct,updateQuantity);
        log.debug("update {}", orderProduct);
        return orderProductRepository.update(orderProduct);
    }
}
