package com.ulawil.dietapp.food;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

@Embeddable
@Getter
@Setter
public class NutritionalInfo {

    @Min(value = 0, message = "Calories cannot be negative")
    protected double kcal;

    @Min(value = 0, message = "Carbs cannot be negative")
    protected double carbs;

    @Min(value = 0, message = "Protein cannot be negative")
    protected double protein;

    @Min(value = 0, message = "Fat cannot be negative")
    protected double fat;
}
