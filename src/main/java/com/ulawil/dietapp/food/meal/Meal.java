package com.ulawil.dietapp.food.meal;

import com.ulawil.dietapp.food.BaseFood;
import com.ulawil.dietapp.food.ingredient.Ingredient;
import com.ulawil.dietapp.food.meal.eatenmeal.EatenMeal;
import com.ulawil.dietapp.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Meal extends BaseFood {

    private double grams;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.MERGE)
    private Set<Ingredient> ingredients;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.MERGE)
    private Set<EatenMeal> eatenMeals;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
        ingredients = new HashSet<>();
    }

    @PrePersist
    @PreUpdate
    private void setNutritionalValues() {
        grams = ingredients.stream().map(Ingredient::getGrams).reduce(0., Double::sum);
        kcal = ingredients.stream().map(Ingredient::getKcal).reduce(0., Double::sum);
        carbs = ingredients.stream().map(Ingredient::getCarbs).reduce(0., Double::sum);
        protein = ingredients.stream().map(Ingredient::getProtein).reduce(0., Double::sum);
        fat = ingredients.stream().map(Ingredient::getFat).reduce(0., Double::sum);
    }

    public double getGrams() {
        return grams;
    }

    public void setGrams(double grams) {
        this.grams = grams;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<EatenMeal> getEatenMeals() {
        return eatenMeals;
    }

    public void setEatenMeals(Set<EatenMeal> eatenMeals) {
        this.eatenMeals = eatenMeals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
