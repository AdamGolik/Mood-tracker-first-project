package com.example.demo.controller.user;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5174")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    // PUT: Aktualizacja danych użytkownika
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userRequest, Principal principal) {
        return userRepository.findById(id).map(user -> {
            // Sprawdzenie, czy użytkownik ma prawo edytować tego użytkownika
            if (!user.getUsername().equals(principal.getName())) {
                return ResponseEntity.status(403).body("You do not have permission to update this user.");
            }

            // Aktualizacja danych użytkownika
            user.setUsername(userRequest.getUsername());
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }).orElse(ResponseEntity.status(404).body("User not found!"));
    }

    // DELETE: Usunięcie użytkownika
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Principal principal) {
        return userRepository.findById(id).map(user -> {
            // Sprawdzenie, czy użytkownik ma prawo usunąć tego użytkownika
            if (!user.getUsername().equals(principal.getName())) {
                return ResponseEntity.status(403).body("You do not have permission to delete this user.");
            }
            userRepository.delete(user);
            return ResponseEntity.ok("User deleted successfully.");
        }).orElse(ResponseEntity.status(404).body("User not found!"));
    }
}