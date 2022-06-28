package com.ulawil.dietapp.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulawil.dietapp.ingredient.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String name;
    private double kcal;
    private double carbs;
    private double protein;
    private double fat;
    @OneToMany(mappedBy = "food100g")
    @JsonIgnore
    private List<Ingredient> ingredients;
}
