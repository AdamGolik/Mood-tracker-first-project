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

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:5174") // Włącz CORS dla tego kontrolera
public class Controller {

    @Autowired
    private MoodRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Pobranie wszystkich nastrojów
    @GetMapping
    public List<Mood> getAllMoods() {
        return repository.findAll();
    }

    // Pobranie nastroju po ID
    @GetMapping("/{id}")
    public Mood getMoodById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // Dodanie nowego nastroju
    @PostMapping
    public Mood createMood(@RequestBody Mood mood) {
        return repository.save(mood);
    }

    // Aktualizacja istniejącego nastroju
    @PutMapping
    public Mood updateMood(@RequestBody Mood mood) {
        return repository.save(mood);
    }

    // Usunięcie nastroju po ID
    @DeleteMapping("/{id}")
    public void deleteMood(@PathVariable Long id) {
        repository.deleteById(id);
    }

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

    // Dodanie nowego nastroju zalogowanego użytkownika
    @PostMapping("/moods")
    public Mood addMood(@RequestBody Mood mood, Principal principal) {
        // Pobranie zalogowanego użytkownika
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + principal.getName()));

        // Ustawienie użytkownika w obiekcie Mood
        mood.setUser(user);

        // Zapis obiektu Mood w bazie danych
        return repository.save(mood);
    }
}