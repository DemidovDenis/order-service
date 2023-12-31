package ru.demidov.orderservice.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.demidov.orderservice.entity.Order;

public interface OrderCustomRepository extends JpaSpecificationExecutor<Order> {
}
