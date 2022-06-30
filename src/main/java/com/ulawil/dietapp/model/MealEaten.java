package com.ulawil.dietapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class MealEaten {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @Min(value = 0, message = "Portion cannot be negative")
    private double portion;

    private LocalDate dateEaten;

    @PrePersist
    void setDateEatenToToday() {
        dateEaten = LocalDate.now();
    }

    public double getGrams() {
        return portion;
    }

    public double getKcal() {
        return meal.getKcal() * portion / meal.getGrams();
    }

    public double getCarbs() {
        return meal.getCarbs() * portion / meal.getGrams();
    }

    public double getProtein() {
        return meal.getProtein() * portion / meal.getGrams();
    }

    public double getFat() {
        return meal.getFat() * portion / meal.getGrams();
    }
}
