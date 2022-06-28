package com.ulawil.dietapp.meal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulawil.dietapp.ingredient.Ingredient;
import com.ulawil.dietapp.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MealEaten> mealsEaten;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
        ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredient.setMeal(this);
        ingredients.add(ingredient);
    }

    public Double getGrams() {
        Double grams = 0.;
        return ingredients.stream().map(Ingredient::getGrams).reduce(grams, Double::sum);
    }

    public double getKcal() {
        Double kcal = 0.;
        return ingredients.stream().map(Ingredient::getKcal).reduce(kcal, Double::sum);
    }

    public double getCarbs() {
        Double kcal = 0.;
        return ingredients.stream().map(Ingredient::getCarbs).reduce(kcal, Double::sum);
    }

    public double getProtein() {
        Double kcal = 0.;
        return ingredients.stream().map(Ingredient::getProtein).reduce(kcal, Double::sum);
    }

    public double getFat() {
        Double kcal = 0.;
        return ingredients.stream().map(Ingredient::getFat).reduce(kcal, Double::sum);
    }
}
