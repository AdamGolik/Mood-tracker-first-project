package com.example.demo.controller;

import com.example.demo.models.Mood;
import com.example.demo.models.User;
import com.example.demo.service.MoodService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/moods")
public class MoodController {

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
}