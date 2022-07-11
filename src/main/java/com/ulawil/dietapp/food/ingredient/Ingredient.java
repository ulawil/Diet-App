package com.ulawil.dietapp.food.ingredient;

import com.ulawil.dietapp.food.NutritionalInfo;
import com.ulawil.dietapp.food.Food;
import com.ulawil.dietapp.food.meal.Meal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @NotBlank(message = "Name cannot be empty")
    protected String name;

    @Embedded
    NutritionalInfo nutritionalInfo;

    @Min(value = 0, message = "Amount cannot be negative!")
    private double grams;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    public Ingredient(Food food, double grams) {
        name = food.getName();
        this.grams = grams;
        nutritionalInfo = new NutritionalInfo();
        nutritionalInfo.setKcal(food.getNutritionalInfo().getKcal() * grams / 100.);
        nutritionalInfo.setCarbs(food.getNutritionalInfo().getCarbs() * grams / 100.);
        nutritionalInfo.setProtein(food.getNutritionalInfo().getProtein() * grams / 100.);
        nutritionalInfo.setFat(food.getNutritionalInfo().getFat() * grams / 100.);
    }
}
