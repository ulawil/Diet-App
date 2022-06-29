package com.ulawil.dietapp.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulawil.dietapp.ingredient.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "food100g")
public class Food100g {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Food name cannot be empty")
    private String name;

    @Min(value = 0, message = "Calories cannot be negative")
    private double kcal;

    @Min(value = 0, message = "Carbs cannot be negative")
    private double carbs;

    @Min(value = 0, message = "Protein cannot be negative")
    private double protein;

    @Min(value = 0, message = "Fat cannot be negative")
    private double fat;

    @OneToMany(mappedBy = "food100g")
    @JsonIgnore
    private List<Ingredient> ingredients;
}
