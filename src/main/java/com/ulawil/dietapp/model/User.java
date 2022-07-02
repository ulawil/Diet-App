package com.ulawil.dietapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean locked;

    private boolean enabled;

    @Min(value = 0, message = "Calories cannot be negative")
    private double dailyKcalGoal;

    @Min(value = 0, message = "Carbs cannot be negative")
    private double dailyCarbsGoalPct;

    @Min(value = 0, message = "Protein cannot be negative")
    private double dailyProteinGoalPct;

    @Min(value = 0, message = "Fat cannot be negative")
    private double dailyFatGoalPct;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Meal> meals;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<MealEaten> mealsEaten;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // temp
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // temp
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
