package com.ulawil.dietapp.model;

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
    @OneToOne
    @JoinColumn(name = "doe_id")
    private DayOfEating dayOfEating;
    @OneToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;
    private LocalDateTime dateEaten; // generate when saving to database
}
