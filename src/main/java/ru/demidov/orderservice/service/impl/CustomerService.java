package ru.demidov.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demidov.orderservice.entity.Customer;
import ru.demidov.orderservice.entity.Role;
import ru.demidov.orderservice.repository.impl.CustomerRepositoryImpl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepositoryImpl customerRepository;

    @Autowired
    public CustomerService(CustomerRepositoryImpl customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("findByUserName {}", username);
        Optional<Customer> customer = customerRepository.findByUserName(username);

        if (customer.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new User(customer.get().getUsername(), customer.get().getPassword(), getAuthorities(customer.get().getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }
}
