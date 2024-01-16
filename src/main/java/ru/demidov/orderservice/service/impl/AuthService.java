package ru.demidov.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.demidov.orderservice.dto.CustomerDto;
import ru.demidov.orderservice.dto.CustomerRequest;
import ru.demidov.orderservice.dto.RegisterCustomerDto;
import ru.demidov.orderservice.entity.Customer;
import ru.demidov.orderservice.repository.impl.CustomerRepositoryImpl;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomerDetailsService detailsService;
    private final RegistrationService registrationService;
    private final CustomerRepositoryImpl customerRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       CustomerDetailsService detailsService,
                       RegistrationService registrationService,
                       CustomerRepositoryImpl customerRepository) {
        this.authenticationManager = authenticationManager;
        this.detailsService = detailsService;
        this.registrationService = registrationService;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<?> loginAuth(@RequestBody CustomerRequest customerRequest) {
        try {
            System.out.println("customerRequest authenticate 1");
            log.debug("customerRequest authenticate {}",
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customerRequest.getUsername(), customerRequest.getPassword()))
            );
            System.out.println("customerRequest authenticate 2");
        }catch (BadCredentialsException exception){
            System.out.println("customerRequest authenticate error");
            log.error("login not found {}", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        System.out.println("customerRequest authenticate 3");

        log.debug("userDetails loadUserByUsername{}", customerRequest.getUsername());
        UserDetails userDetails = detailsService.loadUserByUsername(customerRequest.getUsername());
        System.out.println("customerRequest authenticate 4");

        return ResponseEntity.status(HttpStatus.OK).body(userDetails.getUsername());
    }

    public ResponseEntity<?> createNewCustomer(@RequestBody RegisterCustomerDto registerCustomerDto) {
        if (!registerCustomerDto.getPassword().equals(registerCustomerDto.getConfirmPassword())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (customerRepository.findByUserName(registerCustomerDto.getUsername()).isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.debug("customer register {}", registerCustomerDto);
        Customer customer = registrationService.register(registerCustomerDto);

        return ResponseEntity.ok(new CustomerDto(customer.getId(), customer.getUsername()));
    }
}