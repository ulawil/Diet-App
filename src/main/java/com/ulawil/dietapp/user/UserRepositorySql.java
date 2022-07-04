package com.ulawil.dietapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepositorySql extends UserRepository, JpaRepository<User, Integer> {
    @Override
    Optional<User> findByEmail(String username);
}
