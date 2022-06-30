package com.ulawil.dietapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private LocalDate dateEaten;

    @PrePersist
    void setDateEatenToToday() {
        dateEaten = LocalDate.now();
    }

    public Double getGrams() {
        return meal.getGrams();
    }

    public double getKcal() {
        return meal.getKcal();
    }

    public double getCarbs() {
        return meal.getCarbs();
    }

    public double getProtein() {
        return meal.getProtein();
    }

    public double getFat() {
        return meal.getFat();
    }
}
