package ru.demidov.orderservice.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private Double price;
}