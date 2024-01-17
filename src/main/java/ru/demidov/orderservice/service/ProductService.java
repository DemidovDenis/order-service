package ru.demidov.orderservice.service;

import ru.demidov.orderservice.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProduct(Long id);
    Product save(Product product);
}
