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
import ru.demidov.orderservice.entity.Role;
import ru.demidov.orderservice.repository.RoleRepository;


@Slf4j
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final EntityManager entityManager;

    @Autowired
    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findByName(String roleName) {
        log.debug("find role by name {}", roleName);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> roleCriteriaQuery = criteriaBuilder.createQuery(Role.class);

        Root<Role> roleRoot = roleCriteriaQuery.from(Role.class);
        roleCriteriaQuery.select(roleRoot);
        roleCriteriaQuery.where(criteriaBuilder.equal(roleRoot.get("name"),roleName));
        TypedQuery<Role> query = entityManager.createQuery(roleCriteriaQuery);

        try {
            query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }

        return query.getSingleResult();
    }
}
