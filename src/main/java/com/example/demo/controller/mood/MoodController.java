package com.example.demo.controller.mood;

import com.example.demo.models.Mood;
import com.example.demo.models.User;
import com.example.demo.repository.MoodRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MoodService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/moods")
@CrossOrigin(origins = "http://localhost:5174")
public class MoodController {

    @Autowired
    private MoodRepository repository;

    @Autowired
    private UserRepository userRepository;

    private final MoodService moodService;
    private final UserService userService;

    public MoodController(MoodService moodService, UserService userService) {
        this.moodService = moodService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Mood>> getMoods(Authentication authentication) {
        // Pobranie aktualnie zalogowanego użytkownika
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        // Znalezienie wszystkich nastrojów użytkownika
        List<Mood> moods = moodService.findMoodsByUser(user.getId());
        return ResponseEntity.ok(moods);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMood(@PathVariable Long id, @RequestBody Mood moodRequest, Principal principal) {
        // Weryfikacja danych wejściowych
        if (!isValidMoodRequest(moodRequest)) {
            return ResponseEntity.badRequest().body("Invalid mood data.");
        }

        return repository.findById(id).map(mood -> {
            if (!mood.getUser().getUsername().equals(principal.getName())) {
                return ResponseEntity.status(403).body("You do not have permission to update this mood.");
            }
            mood.setMood(moodRequest.getMood());
            mood.setDescription(moodRequest.getDescription());
            mood.setAlcohol(moodRequest.getAlcohol());
            mood.setSugar(moodRequest.getSugar());
            mood.setHealth(moodRequest.getHealth());
            mood.setWorkout(moodRequest.getWorkout());
            mood.setSleep(moodRequest.getSleep());
            repository.save(mood);
            return ResponseEntity.ok(mood);
        }).orElse(ResponseEntity.status(404).body("Mood not found."));
    }

    private boolean isValidMoodRequest(Mood moodRequest) {
        // Sprawdź prawidłowość danych, np. czy wartości są null, czy są zgodne z typem
        return moodRequest.getMood() != null &&
                moodRequest.getHealth() != null &&
                moodRequest.getHealth() >= 0 && moodRequest.getHealth() <= 10 &&
                moodRequest.getSleep() != null &&
                moodRequest.getSleep() >= 0 && moodRequest.getSleep() <= 24;
    }

    // Usunięcie istniejącego nastroju (tylko zalogowany właściciel)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMood(@PathVariable Long id, Principal principal) {
        return repository.findById(id).map(mood -> {
            // Sprawdzenie, czy zalogowany użytkownik jest właścicielem nastroju
            if (!mood.getUser().getUsername().equals(principal.getName())) {
                return ResponseEntity.status(403).body("You do not have permission to delete this mood.");
            }
            repository.delete(mood);
            return ResponseEntity.ok("Mood deleted successfully.");
        }).orElse(ResponseEntity.status(404).body("Mood not found!"));
    }

    // Dodanie nowego nastroju dla zalogowanego użytkownika
    @PostMapping
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