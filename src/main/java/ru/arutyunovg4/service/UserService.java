package ru.arutyunovg4.service;

import jakarta.transaction.Transactional;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.arutyunovg4.entity.User;
import ru.arutyunovg4.entity.UserRole;
import ru.arutyunovg4.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(int id){
        return  userRepository.findById(id);
    }

    public List<User> findAllByRole(Iterable<UserRole> roles){
        return userRepository.findAllByRoleInOrderById(roles);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void updateRole(int id, UserRole newRole){
        userRepository.updateRole(id, newRole);
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }
    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
       return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(()-> new IllegalArgumentException("User with email = "+ email + " not found"));
    }

}