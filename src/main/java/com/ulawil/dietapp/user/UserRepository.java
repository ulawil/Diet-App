package com.ulawil.dietapp.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String username);

    Optional<User> findById(int id);

    User save(User user);
}
