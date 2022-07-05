package com.ulawil.dietapp.food.ingredient;

import com.ulawil.dietapp.food.BaseFood;
import com.ulawil.dietapp.food.Food100g;
import com.ulawil.dietapp.food.meal.Meal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
public class Ingredient extends BaseFood {

    @Min(value = 0, message = "Amount cannot be negative!")
    private double grams;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    public Ingredient() {
    }

    public Ingredient(Food100g food100g, double amount) {
        name = food100g.getName();
        grams = amount;
        kcal = food100g.getKcal() * grams / 100.;
        carbs = food100g.getCarbs() * grams / 100.;
        protein = food100g.getProtein() * grams / 100.;
        fat = food100g.getFat() * grams / 100.;
    }

    public double getGrams() {
        return grams;
    }

    public void setGrams(double grams) {
        this.grams = grams;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
