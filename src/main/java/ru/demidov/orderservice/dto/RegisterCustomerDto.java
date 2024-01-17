package ru.demidov.orderservice.dto;

import lombok.Data;

@Data
public class RegisterCustomerDto {
    private String username;
    private String password;
    private String confirmPassword;
}

