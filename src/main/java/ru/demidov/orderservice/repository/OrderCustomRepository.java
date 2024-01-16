package ru.demidov.orderservice.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.demidov.orderservice.entity.Order;

@Repository
public interface OrderCustomRepository extends JpaSpecificationExecutor<Order> {
}
