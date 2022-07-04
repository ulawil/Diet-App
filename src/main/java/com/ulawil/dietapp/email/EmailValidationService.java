package com.ulawil.dietapp.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

import org.apache.commons.validator.routines.EmailValidator;

@Service
public class EmailValidationService implements Predicate<String> {
    @Override
    public boolean test(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
