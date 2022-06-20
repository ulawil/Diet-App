package com.ulawil.dietapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private short kcal;
    @OneToMany(mappedBy = "meal")
    private Set<Ingredient> ingredients;

    @PrePersist
    private void CalculateKcal() {
        kcal = ingredients.stream()
                .map(Ingredient::getFood)
                .map(Food::getKcal100g)
                .reduce((k1, k2) -> (short)(k1+k2)).get();
    }
}
