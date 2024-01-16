package ru.demidov.orderservice.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.entity.OrderProduct;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    public final EntityManager entityManager;

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> findAll(){
        log.info("find list orders");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> orderCriteriaQuery = criteriaBuilder.createQuery(Order.class);

        Root<Order> orderRoot = orderCriteriaQuery.from(Order.class);
        orderCriteriaQuery.select(orderRoot);
        TypedQuery<Order> query = entityManager.createQuery(orderCriteriaQuery);

        return query.getResultList();
    }

    @Override
    public List<Product> findProductsByIdOrder(Long id) {
        log.info("find product by order id{}", id);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> orderCriteriaQuery = criteriaBuilder.createQuery(Order.class);

        Root<Order> orderRoot = orderCriteriaQuery.from(Order.class);
        orderCriteriaQuery.select(orderRoot);
        orderCriteriaQuery.where(criteriaBuilder.equal(orderRoot.get("id"), id));
        TypedQuery<Order> query = entityManager.createQuery(orderCriteriaQuery);
        Order order = query.getSingleResult();

        List<Product> productList = new ArrayList<>();
        for (OrderProduct product : order.getOrderProducts()) {
            productList.add(product.getProduct());
        }

        return productList;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        log.info("save order");
        entityManager.persist(order);

        return order;
    }

    @Override
    @Transactional
    public Order update(Order order) {
        log.info("update order");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Order> orderCriteriaUpdate = criteriaBuilder.createCriteriaUpdate(Order.class);

        Root<Order> orderRoot = orderCriteriaUpdate.from(Order.class);
        orderCriteriaUpdate
                .set(orderRoot.get("id"), order.getId())
                .set(orderRoot.get("status"), order.getStatus())
                .where(criteriaBuilder.equal(orderRoot.get("id"),order.getId()));
        entityManager.createQuery(orderCriteriaUpdate).executeUpdate();

        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        log.info("find order by id{}", id);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> orderCriteriaQuery = criteriaBuilder.createQuery(Order.class);

        Root<Order> orderRoot = orderCriteriaQuery.from(Order.class);
        orderCriteriaQuery.select(orderRoot);
        orderCriteriaQuery.where(criteriaBuilder.equal(orderRoot.get("id"), id));
        TypedQuery<Order> query = entityManager.createQuery(orderCriteriaQuery);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public void delete(Order order) {
        log.info("delete order");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Order> orderCriteriaDelete = criteriaBuilder.createCriteriaDelete(Order.class);

        Root<Order> orderRoot = orderCriteriaDelete.from(Order.class);
        orderCriteriaDelete.where(criteriaBuilder.equal(orderRoot.get("id"), order.getId()));
        log.debug("delete order.getId {}", order.getId());

        entityManager.createQuery(orderCriteriaDelete).executeUpdate();
    }
}
