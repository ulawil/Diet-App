package com.ulawil.dietapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public Double getGrams() {
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
