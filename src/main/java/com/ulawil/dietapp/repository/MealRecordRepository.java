package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.MealRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealRecordRepository extends JpaRepository<MealRecord, Integer> {
    List<MealRecord> findByDateEatenBetween(LocalDateTime dateEaten, LocalDateTime dateEaten2);
}
