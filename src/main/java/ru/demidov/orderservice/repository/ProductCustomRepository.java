package ru.demidov.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.demidov.orderservice.entity.Product;

@Repository
public interface ProductCustomRepository extends JpaRepository<Product, Long> {
}
