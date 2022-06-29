package com.ulawil.dietapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
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
        return food100g.getKcal() * grams / 100.;
    }

    public double getCarbs() {
        return food100g.getCarbs() * grams / 100.;
    }

    public double getProtein() {
        return food100g.getProtein() * grams / 100.;
    }

    public double getFat() {
        return food100g.getFat() * grams / 100.;
    }
}
