package com.ulawil.dietapp.service;

import com.ulawil.dietapp.model.ConfirmationToken;
import com.ulawil.dietapp.model.UserRole;
import com.ulawil.dietapp.repository.ConfirmationTokenService;
import com.ulawil.dietapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.model.DTO.UserDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User saveUser(User userToSave) {
        return userRepository.save(userToSave);
    }

    public void enableUser(String email) {
        User toEnable = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        toEnable.setEnabled(true);
        userRepository.save(toEnable);
    }

    public String signUp(UserDTO userDTO) {
        boolean userExists = userRepository.findByEmail(userDTO.getEmail()).isPresent();
        if(userExists) {
            throw new IllegalStateException("Email taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        User newUser = userDTO.toUser();
        newUser.setRole(UserRole.USER);
        userRepository.save(newUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                newUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof User)) {
            return Optional.empty();
        }
        return userRepository.findById(((User) principal).getId());
    }
}
