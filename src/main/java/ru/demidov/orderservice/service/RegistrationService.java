package ru.demidov.orderservice.service;

import ru.demidov.orderservice.dto.RegisterCustomerDto;
import ru.demidov.orderservice.entity.Customer;

public interface RegistrationService {
    Customer register(RegisterCustomerDto registerCustomerDto);
}
