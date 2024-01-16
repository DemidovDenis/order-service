package ru.demidov.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.demidov.orderservice.entity.Customer;
import ru.demidov.orderservice.repository.impl.CustomerRepositoryImpl;
import ru.demidov.orderservice.security.CustomerDetails;

import java.util.Optional;

@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepositoryImpl customerRepository;

    @Autowired
    public CustomerDetailsService(CustomerRepositoryImpl customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("findByUserName {}", username);
        Optional<Customer> customer = customerRepository.findByUserName(username);

        if (customer.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomerDetails(customer.get());
    }
}
