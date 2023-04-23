package ru.skypro.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.avito.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);

}
