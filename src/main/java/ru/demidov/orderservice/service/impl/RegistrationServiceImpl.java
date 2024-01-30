package ru.demidov.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.demidov.orderservice.dto.RegisterCustomerDto;
import ru.demidov.orderservice.entity.Customer;
import ru.demidov.orderservice.repository.impl.CustomerRepositoryImpl;
import ru.demidov.orderservice.repository.impl.RoleRepositoryImpl;
import ru.demidov.orderservice.service.RegistrationService;

import java.util.Collections;

@Slf4j
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final CustomerRepositoryImpl customerRepository;
    private final RoleRepositoryImpl roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(CustomerRepositoryImpl customerRepository, RoleRepositoryImpl roleRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer register(RegisterCustomerDto registerCustomerDto) {
        log.info("register customer");
        Customer customer = new Customer();
        customer.setUsername(registerCustomerDto.getUsername());
        customer.setPassword(passwordEncoder.encode(registerCustomerDto.getPassword()));
        customer.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        log.debug("save customer {}", customer);
        return customerRepository.save(customer);
    }
}