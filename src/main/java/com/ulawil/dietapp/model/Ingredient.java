package com.ulawil.dietapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ingredients")
public class Ingredient {
    @EmbeddedId
    IngredientKey id;

    @ManyToOne
    @MapsId("mealId")
    @JoinColumn(name = "meal_id")
    Meal meal;

    @ManyToOne
    @MapsId("foodId")
    @JoinColumn(name = "food_id")
    Food food;

    int grams;
}
