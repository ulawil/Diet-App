package com.ulawil.dietapp.food.meal;

import com.ulawil.dietapp.food.NutritionalInfo;
import com.ulawil.dietapp.food.ingredient.Ingredient;
import com.ulawil.dietapp.food.meal.eatenmeal.EatenMeal;
import com.ulawil.dietapp.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @NotBlank(message = "Name cannot be empty")
    protected String name;

    @Embedded
    NutritionalInfo nutritionalInfo;

    private double grams;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.PERSIST)
    private Set<Ingredient> ingredients;

    @OneToMany(mappedBy = "meal")
    private Set<EatenMeal> eatenMeals;

    @ManyToOne
    private User user;

    public Meal() {
        ingredients = new HashSet<>();
        nutritionalInfo = new NutritionalInfo();
    }

    @PrePersist
    @PreUpdate
    private void setNutritionalValuesAndGrams() {
        grams = ingredients.stream().map(Ingredient::getGrams).reduce(0., Double::sum);
        nutritionalInfo.setKcal(ingredients.stream().map(i -> i.getNutritionalInfo().getKcal()).reduce(0., Double::sum));
        nutritionalInfo.setCarbs(ingredients.stream().map(i -> i.getNutritionalInfo().getCarbs()).reduce(0., Double::sum));
        nutritionalInfo.setProtein(ingredients.stream().map(i -> i.getNutritionalInfo().getProtein()).reduce(0., Double::sum));
        nutritionalInfo.setFat(ingredients.stream().map(i -> i.getNutritionalInfo().getFat()).reduce(0., Double::sum));
    }
}
