package com.ulawil.dietapp.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulawil.dietapp.meal.Meal;
import com.ulawil.dietapp.meal.MealEaten;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    private boolean locked;
    private boolean enabled = true; // todo: enable when email validated
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Meal> meals;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<MealEaten> mealsEaten;

    public User(String firstName,
                String lastName,
                String email,
                String password,
                UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

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
