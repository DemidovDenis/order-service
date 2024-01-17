package ru.demidov.orderservice.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.demidov.orderservice.entity.Customer;
import ru.demidov.orderservice.repository.CustomerRepository;

import java.util.Optional;

@Slf4j
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final EntityManager entityManager;

    @Autowired
    public CustomerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Customer> findByUserName(String username) {
        log.debug("find customer by name {}", username);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> customerCriteriaQuery = criteriaBuilder.createQuery(Customer.class);

        Root<Customer> customerRoot = customerCriteriaQuery.from(Customer.class);
        customerCriteriaQuery.select(customerRoot);
        customerCriteriaQuery.where(criteriaBuilder.equal(customerRoot.get("username"),username));
        TypedQuery<Customer> query = entityManager.createQuery(customerCriteriaQuery);

        try {
            query.getSingleResult();
        }catch (NoResultException e){
            return Optional.empty();
        }

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        log.debug("save customer {}", customer);
        entityManager.persist(customer);

        return customer;
    }
}

