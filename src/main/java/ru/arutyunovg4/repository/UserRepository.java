package ru.arutyunovg4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.arutyunovg4.entity.User;

import java.util.Optional;
    @Repository
    public interface UserRepository extends JpaRepository<User, Integer> {
        Optional<User> findByEmailIgnoreCase(String email);
    }

