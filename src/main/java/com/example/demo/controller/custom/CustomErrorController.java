package com.example.demo.controller.custom;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError() {
        // Własna obsługa błędu
        return ResponseEntity.status(403).body("Błąd: Brak dostępu lub niepoprawne dane logowania.");
    }
}