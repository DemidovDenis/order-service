package ru.demidov.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demidov.orderservice.dto.ProductDto;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.repository.impl.ProductRepositoryImpl;
import ru.demidov.orderservice.service.ProductService;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepositoryImpl productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        log.debug("getProduct by id {}", id);
        return productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public ResponseEntity<Product> create(ProductDto productDto) {
        log.info("Create products to the database");
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        log.debug("create product {}", productDto);
        productRepository.save(product);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
}
