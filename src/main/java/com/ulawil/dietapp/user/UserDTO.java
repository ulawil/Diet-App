package com.ulawil.dietapp.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    public User toUser() {
        return new User(
                firstName,
                lastName,
                email,
                password,
                role
        );
    }
}
