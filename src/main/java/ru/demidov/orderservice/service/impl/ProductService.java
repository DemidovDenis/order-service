package ru.demidov.orderservice.service.impl;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    public final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        System.out.println("------ProductService----findAll------");
        return productRepository.findAll()
                .stream()
                .map(this::buildProductResponse)
                .toList();
    }

    private Product buildProductResponse(@NotNull Product product) {
        System.out.println("-----buildProductResponse------");
        return new Product()
                .setId(product.getId())
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setPrice(product.getPrice());
    }
}
