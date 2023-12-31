package ru.demidov.orderservice.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.demidov.orderservice.entity.User;

public interface UserCustomRepository extends JpaSpecificationExecutor<User> {
}
