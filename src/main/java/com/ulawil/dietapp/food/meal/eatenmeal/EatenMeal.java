package com.ulawil.dietapp.food.meal.eatenmeal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulawil.dietapp.food.meal.Meal;
import com.ulawil.dietapp.user.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
public class EatenMeal {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }

    public LocalDate getDateEaten() {
        return dateEaten;
    }

    public void setDateEaten(LocalDate dateEaten) {
        this.dateEaten = dateEaten;
    }
}
