package ttv.poltoraha.pivka.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.repository.MyUserRepository;
import ttv.poltoraha.pivka.security.CustomUserDetails;

// Рест контроллер для авторизации пользователей

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MyUserRepository  myUserRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            if (userDetails.needsPasswordReset()) {

                return ResponseEntity.ok("Password reset required for user: " + username);

            }

            return ResponseEntity.ok("User " + username + " logged in successfully!");

        } catch (Exception e) {

            return ResponseEntity.status(401).body("Invalid username or password!");

        }

    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String newPassword) {

        MyUser user = myUserRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.isNeedsPasswordReset()) {
            return ResponseEntity.ok("Password reset is not required for this user.");
        }


        user.setPassword(passwordEncoder.encode(newPassword));
        user.setNeedsPasswordReset(false);
        myUserRepository.save(user);

        return ResponseEntity.ok("Password reset successfully!");

    }
}
