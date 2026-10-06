package ru.arutyunovg4.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.arutyunovg4.entity.User;
import ru.arutyunovg4.repository.UserRepository;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user){
        userRepository.save(user);
    }
    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
       return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(()-> new IllegalArgumentException("User with email = "+ email + " not found"));
    }

}