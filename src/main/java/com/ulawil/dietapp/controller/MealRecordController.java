package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.MealRecord;
import com.ulawil.dietapp.repository.MealRecordRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mealRecords")
public class MealRecordController {

    private final MealRecordRepository mealRecordRepository;

    public MealRecordController(MealRecordRepository mealRecordRepository) {
        this.mealRecordRepository = mealRecordRepository;
    }

    @GetMapping
    ResponseEntity<List<MealRecord>> readRecords() {
        return ResponseEntity.ok(mealRecordRepository.findAll());
    }
}
