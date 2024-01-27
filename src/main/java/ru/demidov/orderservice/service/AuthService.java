package ru.demidov.orderservice.service;

import org.springframework.http.ResponseEntity;
import ru.demidov.orderservice.dto.CustomerRequest;
import ru.demidov.orderservice.dto.RegisterCustomerDto;

public interface AuthService {
    ResponseEntity<?> loginAuth(CustomerRequest customerRequest);
    ResponseEntity<?> createNewCustomer(RegisterCustomerDto registerCustomerDto);
}
