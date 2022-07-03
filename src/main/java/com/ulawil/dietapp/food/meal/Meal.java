package com.ulawil.dietapp.food.meal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulawil.dietapp.food.meal.eatenmeal.EatenMeal;
import com.ulawil.dietapp.food.BaseFood;
import com.ulawil.dietapp.food.ingredient.Ingredient;
import com.ulawil.dietapp.user.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Meal extends BaseFood {

    @Min(value = 0, message = "Amount cannot be negative")
    private double amount;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Ingredient> ingredients;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<EatenMeal> eatenMeals;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
        ingredients = new HashSet<>();
    }

    @PrePersist
    private void setNutritionalValues() {
        amount = ingredients.stream().map(Ingredient::getAmount).reduce(0., Double::sum);
        kcal = ingredients.stream().map(Ingredient::getKcal).reduce(0., Double::sum);
        carbs = ingredients.stream().map(Ingredient::getCarbs).reduce(0., Double::sum);
        protein = ingredients.stream().map(Ingredient::getProtein).reduce(0., Double::sum);
        fat = ingredients.stream().map(Ingredient::getFat).reduce(0., Double::sum);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
