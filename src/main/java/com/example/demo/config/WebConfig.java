package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Dopasowanie dla wszystkich endpointów
                        .allowedOrigins("http://localhost:5174") // Dozwolone pochodzenie (origin)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Dozwolone metody
                        .allowedHeaders("*") // Zezwolenie na wszystkie nagłówki
                        .allowCredentials(true); // Obsługa ciasteczek/autoryzacji
            }
        };
    }
}