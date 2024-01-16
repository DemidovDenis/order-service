package ru.demidov.orderservice.repository.impl;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.demidov.orderservice.entity.OrderProduct;
import ru.demidov.orderservice.repository.OrderProductRepository;

@Slf4j
@Repository
public class OrderProductRepositoryImpl implements OrderProductRepository {

    public final EntityManager entityManager;

    public OrderProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public OrderProduct save(OrderProduct orderProduct) {
        log.info("save order-product");
        entityManager.persist(orderProduct);

        return orderProduct;
    }

    @Override
    @Transactional
    public OrderProduct update(OrderProduct orderProduct) {
        log.info("update order-product");
        entityManager.merge(orderProduct);

        return orderProduct;
    }
}
