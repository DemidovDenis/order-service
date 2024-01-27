package ru.demidov.orderservice.service;

import org.springframework.http.ResponseEntity;
import ru.demidov.orderservice.dto.ProductDto;
import ru.demidov.orderservice.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProduct(Long id);
    ResponseEntity<Product> create(ProductDto productDto);
}
