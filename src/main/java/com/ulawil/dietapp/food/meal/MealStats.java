package com.ulawil.dietapp.food.meal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MealStats {
    private double grams;
    private double kcal;
    private double carbs;
    private double protein;
    private double fat;
}
