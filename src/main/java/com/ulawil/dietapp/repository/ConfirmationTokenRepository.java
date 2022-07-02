package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenRepository {

    Optional<ConfirmationToken> findByToken(String token);

    ConfirmationToken save(ConfirmationToken token);
}
