package com.ulawil.dietapp.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepositorySql extends ConfirmationTokenRepository,
                                                        JpaRepository<ConfirmationToken, Integer> {
    @Override
    Optional<ConfirmationToken> findByToken(String token);
}
