package ru.demidov.orderservice.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.repository.ProductCustomRepository;

import java.util.List;

@Service
public class ProductService {

    public final ProductCustomRepository productCustomRepository;

    @Autowired
    public ProductService(ProductCustomRepository productCustomRepository) {
        this.productCustomRepository = productCustomRepository;
    }

    public List<Product> findAll(){
        System.out.println("------ProductService----findAll------");
        return productCustomRepository.findAll()
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
