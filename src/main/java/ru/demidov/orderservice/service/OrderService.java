package ru.demidov.orderservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.repository.OrderCustomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    public OrderCustomRepository orderCustomRepository;

    @Autowired
    public EntityManager entityManager;

//    @Autowired
//    public OrderService(OrderCustomRepository orderCustomRepository) {
//        this.orderCustomRepository = orderCustomRepository;
//    }

    public List<Order> findAll(){
        System.out.println("------OrderService----findAll------");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> orderCriteriaQuery = criteriaBuilder.createQuery(Order.class);

        Root<Order> orderRoot = orderCriteriaQuery.from(Order.class);
        TypedQuery<Order> query = entityManager.createQuery(orderCriteriaQuery);
        return query.getResultList();
    }
}
