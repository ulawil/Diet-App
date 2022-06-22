package com.ulawil.dietapp.model;

import javax.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private String name;
    private int grams;
    private int kcal;
    @ManyToOne()
    @JoinColumn(name = "meal_id")
    private Meal meal;

    public Ingredient(Food food, int grams) {
        this.name = food.getName();
        this.grams = grams;
        this.kcal = (int)Math.round((double)(food.getKcal100g()*grams)/100);
    }

    public Ingredient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    // do not make public, will cause infinite recursion!!!
    Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
