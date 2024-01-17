package ru.otus.spring.bookstore.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.bookstore.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
