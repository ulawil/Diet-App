package com.ulawil.dietapp.food.meal.eatenmeal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulawil.dietapp.food.meal.Meal;
import com.ulawil.dietapp.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class EatenMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
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
}
