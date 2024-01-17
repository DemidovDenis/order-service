package ru.demidov.orderservice.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    public final EntityManager entityManager;

    @Autowired
    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Product> findAll() {
        log.debug("find list products");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> productCriteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> productRoot = productCriteriaQuery.from(Product.class);
        productCriteriaQuery.select(productRoot);
        TypedQuery<Product> query = entityManager.createQuery(productCriteriaQuery);

        return query.getResultList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        log.debug("find products by id {}", id);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> productCriteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> productRoot = productCriteriaQuery.from(Product.class);
        productCriteriaQuery.select(productRoot);
        productCriteriaQuery.where(criteriaBuilder.equal(productRoot.get("id"),id));
        TypedQuery<Product> query = entityManager.createQuery(productCriteriaQuery);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public Product save(Product product) {
        log.debug("product save {}", product);
        entityManager.persist(product);

        return product;
    }
}
