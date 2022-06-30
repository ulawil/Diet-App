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

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Ingredient> ingredients;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<MealEaten> mealsEaten;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
        ingredients = new HashSet<>();
    }

    public double getGrams() {
        return ingredients.stream().map(Ingredient::getGrams).reduce(0., Double::sum);
    }

    public double getKcal() {
        return ingredients.stream().map(Ingredient::getKcal).reduce(0., Double::sum);
    }

    public double getCarbs() {
        return ingredients.stream().map(Ingredient::getCarbs).reduce(0., Double::sum);
    }

    public double getProtein() {
        return ingredients.stream().map(Ingredient::getProtein).reduce(0., Double::sum);
    }

    public double getFat() {
        return ingredients.stream().map(Ingredient::getFat).reduce(0., Double::sum);
    }
}
