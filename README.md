# Mood Tracker Application

Mood Tracker to aplikacja webowa umożliwiająca użytkownikom zarządzanie swoimi nastrojami. Projekt bazuje na Spring Boot z użyciem Spring Security oraz relacyjnej bazy danych (np. MySQL) do przechowywania danych użytkowników oraz nastrojów.

---

## 🚀 Funkcjonalności

Aplikacja pozwala użytkownikom na:

1. **Rejestrację nowych użytkowników**:
   Endpointy umożliwiają tworzenie kont użytkowników i przechowywanie ich danych w bazie.

2. **Logowanie i autoryzację**:
   Spring Security zarządza autoryzacją użytkowników oraz ich sesjami.

3. **Dodawanie nastrojów (`Mood`)**:
   Użytkownicy mogą dodawać szczegółowe informacje o swoim samopoczuciu, jak:
   - Nazwa samopoczucia (np. "happy", "sad").
   - Szczegóły dotyczące opisu nastroju.
   - Wpływ różnych czynników (alkohol, cukier, ćwiczenia, sen).

4. **Pobieranie istniejących nastrojów**:
   Użytkownicy mogą przeglądać swoje zapisane nastroje.

5. **Przechowywanie danych w relacyjnej bazie danych**:
   Encje `User` oraz `Mood` są powiązane w relacji `1:N`.

---

## 🛠️ Technologie

- **Backend Framework**: Spring Boot (z wykorzystaniem Spring MVC oraz Spring Data JPA).
- **Baza danych**: MySQL (lub dowolna inna relacyjna baza danych wspierana przez Hibernate).
- **Autoryzacja/Uwierzytelnienie**: Spring Security (HTTP Basic Authentication, BCrypt).
- **Język programowania**: Java 17+.

---

## 📂 Struktura projektu

- **Model/Encje**:
  - `User`: reprezentuje użytkownika systemu.
  - `Mood`: reprezentuje nastrój przypisany do konkretnego użytkownika.
- **Controller**:
  Obsługuje zapytania HTTP (POST, GET, DELETE, itd.) dla operacji na użytkownikach i nastrojach.
- **Repository**:
  Gromadzi operacje na bazie danych dzięki Spring Data JPA.
- **Config**:
  Konfiguracja Spring Security umożliwiająca uwierzytelnianie oraz autoryzację użytkowników.

---

## 📄 Endpointy API

### **Rejestracja Użytkownika**
**URL**: `/register`  
**Metoda HTTP**: `POST`  
**Dane wejściowe (Body)**:
```json
{
  "username": "newUser",
  "password": "securePassword"
}
```
**Odpowiedź**:
```json
{
  "id": 2,
  "username": "newUser",
  "password": "$2a$10$...hashed_password",
  "moods": null
}
```

---

### **Logowanie Użytkownika**
Używamy HTTP Basic Auth, przekazując dane użytkownika w nagłówku.

---

### **Dodawanie Nastroju**
**URL**: `/moods`  
**Metoda HTTP**: `POST`  
**Authentikacja**: Wymagana (`username` i `password`)  
**Dane wejściowe (Body)**:
```json
{
  "mood": "happy",
  "description": "Feels great!",
  "alkohol": false,
  "sugar": true,
  "workout": false,
  "sleep": 8
}
```
**Odpowiedź**:
```json
{
  "id": 1,
  "mood": "happy",
  "description": "Feels great!",
  "alkohol": null,
  "sugar": true,
  "workout": false,
  "sleep": 8,
  "user": {
    "id": 2,
    "username": "newUser",
    "password": "$2a$10$..."
  }
}
```

---

### **Pobierz Wszystkie Nastroje**
**URL**: `/moods`  
**Metoda HTTP**: `GET`  
**Authentikacja**: Wymagana (`username` i `password`)  
**Odpowiedź**:
```json
[
  {
    "id": 1,
    "mood": "happy",
    "description": "Feels great!",
    "alkohol": null,
    "sugar": true,
    "workout": false,
    "sleep": 8
  }
]
```

---

### **Pobierz Nastrój po ID**
**URL**: `/moods/{id}`  
**Metoda HTTP**: `GET`  
**Authentikacja**: Wymagana (`username` i `password`)  
**Odpowiedź**:
```json
{
  "id": 1,
  "mood": "happy",
  "description": "Feels great!",
  "alkohol": null,
  "sugar": true,
  "workout": false,
  "sleep": 8
}
```

---

## ⚙️ Jak uruchomić

1. Skonfiguruj środowisko:
   - Zainstaluj JDK 17+.
   - Skonfiguruj MySQL lub inną relacyjną bazę danych i utwórz schemat.
   - W pliku `application.properties` dodaj szczegóły swojej bazy danych.

2. Zbuduj projekt:
   - Uruchom komendę:
     ```bash
     ./mvnw clean install
     ```

3. Uruchom aplikację:
   - Uruchom komendę:
     ```bash
     ./mvnw spring-boot:run
     ```

4. Używaj API przy pomocy klientów takich jak:
   - **Postman**: Zbuduj i testuj zapytania HTTP.
   - **cURL**: Wyślij żądania z terminala.

---

## 💾 Baza danych

### Schemat `User`
| Pole         | Typ             | Opis                      |
|--------------|-----------------|---------------------------|
| `id`         | `Long`          | Identyfikator użytkownika |
| `username`   | `String`        | Nazwa użytkownika         |
| `password`   | `String`        | Hasło (zakodowane)        |
| `moods`      | `List<Mood>`    | Lista nastrojów           |

### Schemat `Mood`
| Pole         | Typ             | Opis                      |
|--------------|-----------------|---------------------------|
| `id`         | `Long`          | Identyfikator nastroju    |
| `mood`       | `String`        | Nazwa nastroju            |
| `description`| `String`        | Opis                     |
| `alkohol`    | `Boolean`       | Wpływ alkoholu            |
| `sugar`      | `Boolean`       | Wpływ cukru               |
| `workout`    | `Boolean`       | Ćwiczenia fizyczne        |
| `sleep`      | `Integer`       | Czas snu w godzinach      |
| `user`       | `User`          | Powiązany użytkownik      |

---

## 🛡️ Bezpieczeństwo

- Aplikacja korzysta z **Spring Security**:
  - Uwierzytelnianie za pomocą HTTP Basic Auth.
  - Hasła użytkowników kodowane przy użyciu **BCrypt**.
  - Ręczne definiowanie endpointów dostępnych publicznie (np. `/register`).
- CSRF jest domyślnie wyłączony (opcjonalne do konfiguracji).

---

## 📧 Wsparcie

Jeśli potrzebujesz dalszej pomocy, skontaktuj się z autorem projektu.
