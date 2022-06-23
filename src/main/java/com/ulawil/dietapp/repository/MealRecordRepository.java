package com.ulawil.dietapp.repository;

import com.ulawil.dietapp.model.MealRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRecordRepository extends JpaRepository<MealRecord, Integer> {

}
