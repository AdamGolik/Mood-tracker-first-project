package com.example.demo.controller;

import com.example.demo.models.Mood;
import com.example.demo.models.User;
import com.example.demo.repository.MoodRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
// obsługa usera
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:5174") // Włącz CORS dla tego kontrolera
public class Controller {

//    @Autowired
//    private MoodRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Rejestracja użytkownika (z poprawioną ścieżką URL + walidacja)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Sprawdzenie, czy użytkownik już istnieje
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists!");
        }
        // Kodowanie i zapis hasła, a następnie zapis użytkownika
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        // Zwrot statusu 201 z zapisanym obiektem użytkownika
        return ResponseEntity.status(201).body(user);
    }
}