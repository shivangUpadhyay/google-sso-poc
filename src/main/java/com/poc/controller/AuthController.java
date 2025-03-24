package com.poc.controller;

import com.poc.dto.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {

    @GetMapping("/")
    public ResponseEntity<String> homePage() {
        return ResponseEntity.ok("Welcome to Google SSO POC with Spring Boot!");
    }

    @GetMapping("/login-success")
    public ResponseEntity<UserResponseDTO> loginSuccessPage(@AuthenticationPrincipal OAuth2User oauth2User) {
        String name = (String) oauth2User.getAttribute("name");
        String email = (String) oauth2User.getAttribute("email");
        String profilePic = (String) oauth2User.getAttribute("picture");

        UserResponseDTO responseDTO = new UserResponseDTO(name, email, profilePic, "Login successful!");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/login-failure")
    public ResponseEntity<String> loginFailurePage() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Please try again.");
    }

    @GetMapping("/logout-success")
    public ResponseEntity<String> logoutSuccessPage() {
        return ResponseEntity.ok("You have been successfully logged out.");
    }
}
