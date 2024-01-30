package ru.demidov.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.demidov.orderservice.dto.CustomerDto;
import ru.demidov.orderservice.dto.CustomerRequest;
import ru.demidov.orderservice.dto.RegisterCustomerDto;
import ru.demidov.orderservice.entity.Customer;
import ru.demidov.orderservice.repository.impl.CustomerRepositoryImpl;
import ru.demidov.orderservice.service.AuthService;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final DaoAuthenticationProvider authenticationProvider;
    private final CustomerServiceImpl detailsService;
    private final RegistrationServiceImpl registrationService;
    private final CustomerRepositoryImpl customerRepository;

    @Autowired
    public AuthServiceImpl(
            DaoAuthenticationProvider authenticationProvider,
            CustomerServiceImpl detailsService,
            RegistrationServiceImpl registrationService,
            CustomerRepositoryImpl customerRepository) {
        this.authenticationProvider = authenticationProvider;
        this.detailsService = detailsService;
        this.registrationService = registrationService;
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseEntity<?> loginAuth(@RequestBody CustomerRequest customerRequest) {
        try {
            log.info("customerRequest authenticate");
            log.debug("customerRequest authenticate {}",
                    authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(customerRequest.getUsername(), customerRequest.getPassword()))
            );
        } catch (BadCredentialsException exception) {
            log.error("login not found {}", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        log.debug("userDetails loadUserByUsername{}", customerRequest.getUsername());
        UserDetails userDetails = detailsService.loadUserByUsername(customerRequest.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(userDetails.getUsername());
    }

    @Override
    public ResponseEntity<?> createNewCustomer(@RequestBody RegisterCustomerDto registerCustomerDto) {
        if (!registerCustomerDto.getPassword().equals(registerCustomerDto.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (customerRepository.findByUserName(registerCustomerDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.debug("customer register {}", registerCustomerDto);
        Customer customer = registrationService.register(registerCustomerDto);

        return ResponseEntity.ok(new CustomerDto(customer.getId(), customer.getUsername()));
    }
}