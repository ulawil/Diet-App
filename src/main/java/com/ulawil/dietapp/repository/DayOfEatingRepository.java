package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.DayOfEating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DayOfEatingRepository extends JpaRepository<DayOfEating, Integer> {
    Optional<DayOfEating> findByDateIs(LocalDate date);
}
