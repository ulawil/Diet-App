package com.ulawil.dietapp.service;

import com.ulawil.dietapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.model.UserDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public String signUp(UserDTO userDTO) {
        boolean userExists = userRepository.findByEmail(userDTO.getEmail()).isPresent();
        if(userExists) {
            throw new IllegalStateException("Email taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        userRepository.save(userDTO.toUser());

        // todo: send confirmation mail
        return "";
    }

    public Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof User)) {
            return Optional.empty();
        }
        return userRepository.findById(((User) principal).getId());
    }

    public User saveUser(User userToSave) {
        return userRepository.save(userToSave);
    }
}
