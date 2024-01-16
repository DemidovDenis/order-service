package ru.demidov.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerRequest {
    private String username;
    private String password;
}
