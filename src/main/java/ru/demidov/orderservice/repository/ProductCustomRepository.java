package ru.demidov.orderservice.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.demidov.orderservice.entity.Product;

public interface ProductCustomRepository extends JpaSpecificationExecutor<Product> {
}
