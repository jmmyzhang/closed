package com.example.closed.controller;

import com.example.closed.dto.AuthRequest;
import com.example.closed.dto.RegisterRequest;
import com.example.closed.entity.User;
import com.example.closed.repository.UserRepository;
import com.example.closed.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername());

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }

        if (passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }
    }
}
