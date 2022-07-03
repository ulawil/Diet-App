package com.ulawil.dietapp.registration.token;

import java.util.Optional;

public interface ConfirmationTokenRepository {

    Optional<ConfirmationToken> findByToken(String token);

    ConfirmationToken save(ConfirmationToken token);
}
