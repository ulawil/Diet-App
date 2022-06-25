package com.ulawil.dietapp.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double kcal100g;
    @OneToMany(mappedBy = "food")
    @JsonIgnore
    private List<Ingredient> ingredients;

    public String getKcal100gAsString() {
        return String.format("%.0f", getKcal100g());
    }
}
