package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface SqlUserRepository extends UserRepository, JpaRepository<User, Integer> {
    @Override
    Optional<User> findByEmail(String username);
}
