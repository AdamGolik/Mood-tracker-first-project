
# Mood Tracker API

Mood Tracker API to aplikacja do zarządzania nastrojami użytkownika za pomocą REST API. Projekt oparty jest na Spring Boot, z wykorzystaniem Spring Security i JPA dla obsługi bazy danych.

---

## Funkcjonalności

- Rejestracja użytkowników.
- Uwierzytelnianie użytkowników za pomocą Basic Auth (Spring Security).
- CRUD (Create, Read, Update, Delete) dla zarządzania nastrojami.
- Obsługa danych takich jak: `moodText`, `description`, `alkochol`, `sugar`, `workout`, `sleep`.

---

## Wymagania wstępne

1. **Java** (Java 17 lub nowsza).
2. **Maven** (do budowy projektu).
3. **Baza danych** MySQL/PostgreSQL (lub inna skonfigurowana baza danych).
4. Narzędzia do testowania API, np. **Postman** lub **cURL**.

---

## Instalacja i uruchomienie

1. **Skonfiguruj połączenie z bazą danych** w pliku `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/demo
   spring.datasource.username=your_database_user
   spring.datasource.password=your_database_password

   spring.jpa.hibernate.ddl-auto=update
   ```

2. **Uruchom projekt w terminalu**:
   ```bash
   mvn spring-boot:run
   ```

3. **API jest teraz dostępne** pod adresem:  
   `http://localhost:8080`

---

## Endpointy API

### Rejestracja nowego użytkownika
- **URL**: `POST /register`
- **Opis**: Rejestruje nowego użytkownika.
- **Body (JSON)**:
  ```json
  {
      "username": "newUser",
      "password": "securePassword"
  }
  ```

### Pobranie wszystkich nastrojów użytkownika
- **URL**: `GET /`
- **Opis**: Pobiera wszystkie nastroje zalogowanego użytkownika.
- **Nagłówki**:
  - Authorization: Basic Auth.

### Dodanie nowego nastroju
- **URL**: `POST /moods`
- **Opis**: Dodaje nowy nastrój do zalogowanego użytkownika.
- **Body (JSON)**:
  ```json
  {
      "moodText": "Feeling great!",
      "description": "I had a productive day!",
      "alkochol": 0,
      "sugar": 2,
      "workout": 1,
      "sleep": 7
  }
  ```

### Aktualizacja istniejącego nastroju
- **URL**: `PUT /moods`
- **Opis**: Edytuje istniejący nastrój.
- **Body (JSON)**:
  ```json
  {
      "id": 1,
      "moodText": "Feeling even better!",
      "description": "Amazing day!",
      "alkochol": 0,
      "sugar": 1,
      "workout": 1,
      "sleep": 8
  }
  ```

### Usunięcie nastroju
- **URL**: `DELETE /{id}`
- **Opis**: Usuwa nastroj z podanym ID.

---

## Testowanie za pomocą cURL

### Rejestracja użytkownika
```bash
curl -X POST http://localhost:8080/register \
-H "Content-Type: application/json" \
-d '{
    "username": "newUser",
    "password": "securePassword"
}'
```

### Dodawanie nowego nastroju
```bash
curl -u newUser:securePassword \
-X POST http://localhost:8080/moods \
-H "Content-Type: application/json" \
-d '{
    "moodText": "Feeling amazing!",
    "description": "Great day!",
    "alkochol": 0,
    "sugar": 1,
    "workout": 1,
    "sleep": 8
}'
```

### Pobranie wszystkich nastrojów
```bash
curl -u newUser:securePassword -X GET http://localhost:8080/
```

---

## Testowanie za pomocą Postman

### Rejestracja użytkownika
1. **Metoda**: `POST`
2. **URL**: `http://localhost:8080/register`
3. **Nagłówki**: Content-Type: `application/json`
4. **Body (JSON)**:
   ```json
   {
       "username": "newUser",
       "password": "securePassword"
   }
   ```

### Dodawanie nowego nastroju
1. **Metoda**: `POST`
2. **URL**: `http://localhost:8080/moods`
3. **Nagłówki**:
   - Content-Type: `application/json`
   - Authorization: **Basic Auth** (podaj `username` i `password`).
4. **Body (JSON)**:
   ```json
   {
       "moodText": "Feeling great!",
       "description": "I had a fun day!",
       "alkochol": 1,
       "sugar": 3,
       "workout": 0,
       "sleep": 6
   }
   ```

### Pobranie wszystkich nastrojów
1. **Metoda**: `GET`
2. **URL**: `http://localhost:8080/`
3. **Nagłówki**:
   - Authorization: **Basic Auth** (podaj `username` i `password`).

---

## Uwagi

- Przy każdym żądaniu (poza `/register`) wymagane jest uwierzytelnienie za pomocą Basic Auth (nagłówek `Authorization`).
- Kluczowe pola obsługiwane przez API przy operacjach CRUD:
  - `moodText`: Tekst opisujący nastrój.
  - `description`: Szczegółowy opis.
  - `alkochol`, `sugar`: Wskaźniki spożycia.
  - `workout`: 1 (był trening) lub 0 (brak treningu).
  - `sleep`: Ilość godzin snu.

---

Daj znać, jeśli coś wymaga doprecyzowania! 😊
