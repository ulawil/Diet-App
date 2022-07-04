package com.ulawil.dietapp.registration;

import com.ulawil.dietapp.email.EmailSender;
import com.ulawil.dietapp.email.EmailValidationService;
import com.ulawil.dietapp.registration.token.ConfirmationToken;
import com.ulawil.dietapp.registration.token.ConfirmationTokenService;
import com.ulawil.dietapp.user.UserDTO;
import com.ulawil.dietapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidationService emailValidationService;
    private final EmailSender emailSender;

    public void register(UserDTO userDTO) {
        boolean isValid = emailValidationService.test(userDTO.getEmail());
        if (!isValid) {
            throw new IllegalStateException("Email not valid");
        }
        String token = userService.signUp(userDTO);
        String link = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/confirm?token={token}")
                .buildAndExpand(token)
                .toString();
        emailSender.send(userDTO.getEmail(), buildEmail(userDTO.getFirstName(), link));
    }

    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if(confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed!");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        userService.enableUser(confirmationToken.getUser().getEmail());
    }

    private String buildEmail(String name, String link) {
        return "<div>" +
                "<p>Hi " + name + "! Thank you for registering. Please click on the below link to activate your account:</p>" +
                "<a href=\"" + link + "\">Activate Now</a>" +
                "<p>See you soon!</p>" +
                "</div>";
    }
}
