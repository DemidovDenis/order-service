package ru.demidov.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.service.impl.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    public final ProductService productService;

    @Autowired
    public ProductController(ProductService testService) {
        this.productService = testService;
        System.out.println("constructor ProductController");
    }

    @GetMapping("/all")
    public List<Product> getProduct() {
        System.out.println("-----------getProduct----------------");
        return productService.findAll();
    }
}
