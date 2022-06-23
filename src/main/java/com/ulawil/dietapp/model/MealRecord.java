package com.ulawil.dietapp.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class MealRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Getter(AccessLevel.NONE)
    @OneToOne
    @JoinColumn(name = "doe_id")
    private DayOfEating dayOfEating;
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;
    private LocalDateTime dateEaten;

    @PrePersist
    void setDateEatenToToday() {
        dateEaten = LocalDateTime.now();
    }
}
