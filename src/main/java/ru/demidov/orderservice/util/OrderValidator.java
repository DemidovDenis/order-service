package ru.demidov.orderservice.util;


import ru.demidov.orderservice.dto.OrderProductDto;

import java.util.List;

public interface OrderValidator {
    void validateProductsExistence(List<OrderProductDto> orderProducts);
}
