package ru.demidov.orderservice.repository;

import ru.demidov.orderservice.entity.Role;


public interface RoleRepository {
    Role findByName(String name);
}
