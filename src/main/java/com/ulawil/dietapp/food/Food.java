package com.ulawil.dietapp.food;

import com.ulawil.dietapp.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @NotBlank(message = "Name cannot be empty")
    protected String name;

    @Embedded
    NutritionalInfo nutritionalInfo;

    @ManyToOne
    private User user;

    public Food() {
        nutritionalInfo = new NutritionalInfo();
    }
}
