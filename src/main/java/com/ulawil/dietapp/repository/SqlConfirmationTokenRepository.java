package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SqlConfirmationTokenRepository extends ConfirmationTokenRepository,
                                                        JpaRepository<ConfirmationToken, Integer> {
    @Override
    Optional<ConfirmationToken> findByToken(String token);
}
