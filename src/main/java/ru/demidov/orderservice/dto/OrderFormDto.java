package ru.demidov.orderservice.dto;

import java.util.List;

public class OrderFormDto {
    private List<OrderProductDto> productOrders;

    public List<OrderProductDto> getProductOrders() {
        return productOrders;
    }

    public Integer getQuantity(){
        return productOrders.stream()
                .iterator()
                .next()
                .getQuantity();
    }

    public void setProductOrders(List<OrderProductDto> productOrders) {
        this.productOrders = productOrders;
    }
}
