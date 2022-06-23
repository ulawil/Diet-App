package com.ulawil.dietapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @JsonIgnore
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "meal")
    @JsonIgnore
    private List<MealEaten> mealsEaten;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
        ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setMeal(this);
        kcal += ingredient.getKcal();
        grams += ingredient.getGrams();
    }
}
