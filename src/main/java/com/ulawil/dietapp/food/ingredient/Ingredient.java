package com.ulawil.dietapp.food.ingredient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulawil.dietapp.food.BaseFood;
import com.ulawil.dietapp.food.Food100g;
import com.ulawil.dietapp.food.meal.Meal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
public class Ingredient extends BaseFood {

    @Min(value = 0, message = "Amount cannot be negative")
    private double amount;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "meal_id")
    private Meal meal;

    public Ingredient() {
    }

    public Ingredient(Food100g food100g, double amount) {
        name = food100g.getName();
        this.amount = amount;
        kcal = food100g.getKcal() * amount / 100.;
        carbs = food100g.getCarbs() * amount / 100.;
        protein = food100g.getProtein() * amount / 100.;
        fat = food100g.getFat() * amount / 100.;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
