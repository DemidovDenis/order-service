package ru.demidov.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.demidov.orderservice.dto.RegisterCustomerDto;
import ru.demidov.orderservice.entity.Customer;
import ru.demidov.orderservice.entity.Role;
import ru.demidov.orderservice.repository.impl.CustomerRepositoryImpl;

import java.util.Collections;

@Slf4j
@Service
public class RegistrationService {

    private final CustomerRepositoryImpl customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(CustomerRepositoryImpl customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer register(RegisterCustomerDto registerCustomerDto) {
        log.info("register customer");
        Customer customer = new Customer();
        customer.setUsername(registerCustomerDto.getUsername());
        customer.setPassword(passwordEncoder.encode(registerCustomerDto.getPassword()));
        customer.setRoles(Collections.singletonList(new Role(2L)));
        log.debug("save customer {}", customer);
        return customerRepository.save(customer);
    }
}