package com.ulawil.dietapp.ingredient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulawil.dietapp.food.Food100g;
import com.ulawil.dietapp.meal.Meal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private double grams;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_id")
    private Food100g food100g;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "meal_id")
    private Meal meal;

    public Ingredient(Food100g food100g, double grams) {
        this.food100g = food100g;
        this.grams = grams;
    }

    public double getKcal() {
        return food100g.getKcal()*grams/100.;
    }
}
