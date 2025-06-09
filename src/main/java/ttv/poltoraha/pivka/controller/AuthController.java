package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
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
import ttv.poltoraha.pivka.service.ReaderService;
import ttv.poltoraha.pivka.serviceImpl.ReaderServiceChooser;

// Рест контроллер для авторизации пользователей

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final ReaderServiceChooser chooser;
    private final PasswordEncoder passwordEncoder;
    private final MyUserRepository  myUserRepository;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        ReaderService service = chooser.getReaderService(userDetails.getUsername());

        if (userDetails.needsPasswordReset()) {

            return "Password reset required for user: " + username;
        }

        return "User " + username + " logged in successfully!";
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String username, @RequestParam String newPassword) {
        MyUser user = myUserRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isNeedsPasswordReset()) {
            return "Password reset is not required for this user.";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setNeedsPasswordReset(false);
        myUserRepository.save(user);

        return "Password reset successful!";
    }
}
