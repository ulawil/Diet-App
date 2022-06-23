package com.ulawil.dietapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int grams;
    private int kcal;
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
    private Set<Ingredient> ingredients;

    //@OneToMany
    //private Set<MealRecord> mealRecords;

    public Meal() {
        ingredients = new HashSet<>();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setMeal(this);
        kcal += ingredient.getKcal();
        grams += ingredient.getGrams();
    }
}
