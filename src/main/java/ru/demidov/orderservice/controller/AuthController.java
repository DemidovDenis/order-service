package ru.demidov.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.demidov.orderservice.dto.CustomerRequest;
import ru.demidov.orderservice.dto.RegisterCustomerDto;
import ru.demidov.orderservice.service.impl.AuthService;

@Slf4j
@Tag(name = "Authentication and Registration Controller")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Authentication customer"
    )
    @PostMapping("/login")
    public ResponseEntity<?> loginAuth(@RequestBody CustomerRequest customerRequest) {
        //log.info("Authentication customer {}", customerRequest.getUsername());
        return authService.loginAuth(customerRequest);
    }

    @Operation(
            summary = "Registration customer"
    )
    @PostMapping("/registration")
    public ResponseEntity<?> createNewCustomer(@RequestBody RegisterCustomerDto registerCustomerDto) {
        log.info("Registration customer {}", registerCustomerDto.getUsername());
        return authService.createNewCustomer(registerCustomerDto);
    }
}
