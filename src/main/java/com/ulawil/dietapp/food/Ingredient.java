package com.ulawil.dietapp.food;

import com.ulawil.dietapp.meal.Meal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private String name;
    private int grams;
    private int kcal;
    @Getter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    public Ingredient(Food food, int grams) {
        this.name = food.getName();
        this.grams = grams;
        this.kcal = (int)Math.round((double)(food.getKcal100g()*grams)/100);
    }
}
