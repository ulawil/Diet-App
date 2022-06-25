package com.ulawil.dietapp.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulawil.dietapp.meal.Meal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private double grams;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_id")
    private Food food;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "meal_id")
    private Meal meal;

    public Ingredient(Food food, double grams) {
        this.food = food;
        this.grams = grams;
    }

    public String getGramsAsString() {
        return String.format("%.0f", grams);
    }

    public double getKcal() {
        return food.getKcal100g()*grams/100.;
    }

    public String getKcalAsString() {
        return String.format("%.0f", getKcal());
    }
}
