package com.ulawil.dietapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

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
    @ManyToMany
    @JoinTable( // Hibernate will do it automatically, but will generate 2 joining tables, so it's better to specify
            name = "meal_food",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private Set<Food> foods; // a meal can consist of many foods

    @PrePersist
    private void CalculateKcal() {
        kcal = foods.stream().map(Food::getKcal100g).reduce((k1, k2) -> (short)(k1+k2)).get();
    }
}
