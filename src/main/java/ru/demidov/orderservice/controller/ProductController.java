package ru.demidov.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.demidov.orderservice.dto.ProductDto;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.service.ProductService;

import java.util.List;

@Slf4j
@Tag(name = "Product Controller")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Getting all products from the database"
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Product> getProducts() {
        log.info("Getting all products from the database");
        return productService.getAllProducts();
    }

    @Operation(
            summary = "Adding products to the database"
    )
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        log.info("Adding products to the database");
        return productService.create(productDto);
    }
}

