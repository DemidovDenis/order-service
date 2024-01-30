package ru.demidov.orderservice.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.demidov.orderservice.dto.OrderProductDto;
import ru.demidov.orderservice.service.impl.ProductServiceImpl;
import ru.demidov.orderservice.util.OrderValidator;

import java.util.List;
import java.util.Objects;

@Component
public class OrderValidatorImpl implements OrderValidator {

    private final ProductServiceImpl productService;

    @Autowired
    public OrderValidatorImpl(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Override
    public void validateProductsExistence(List<OrderProductDto> orderProducts) {
        List<OrderProductDto> list = orderProducts
                .stream()
                .filter(op -> Objects.isNull(productService.getProduct(op
                        .getProduct()
                        .getId())))
                .toList();

        if (!CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("Product not found");
        }
    }
}
