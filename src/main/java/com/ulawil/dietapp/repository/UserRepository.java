package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String username);
    Optional<User> findById(int id);
    User save(User user);
}
