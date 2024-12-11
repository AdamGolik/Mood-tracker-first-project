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

1. **Pobierz i zainstaluj Postman** ze strony [Postman Web](https://www.postman.com/).

2. **Utwórz Workspace**:
   - Otwórz aplikację Postman.
   - Stwórz nowy Workspace lub użyj już istniejącego.

3. **Endpointy do przetestowania**:

---

### **1. Rejestracja nowego użytkownika**

#### Szczegóły:
- **Metoda**: `POST`
- **URL**: `http://localhost:8080/register`
- **Nagłówki**:
  - Content-Type: `application/json`
- **Body (JSON)**:
  ```json
  {
      "username": "newUser",
      "password": "securePassword"
  }
  ```

#### Kroki:
1. Wybierz metodę `POST` w Postman.
2. Ustaw URL: `http://localhost:8080/register`.
3. Przejdź do zakładki **Headers** i dodaj:
   ```
   Key: Content-Type 
   Value: application/json
   ```
4. W zakładce **Body** wybierz typ `raw`, a następnie wklej podany JSON.
5. Kliknij `Send`.

---

### **2. Dodawanie nowego nastroju**

#### Szczegóły:
- **Metoda**: `POST`
- **URL**: `http://localhost:8080/moods`
- **Nagłówki**:
  - Content-Type: `application/json`
  - Authorization: Basic Auth (ustaw username i password).
- **Body (JSON)**:
  ```json
  {
      "moodText": "Feeling great!",
      "description": "I had a great day!",
      "alkochol": 0,
      "sugar": 2,
      "workout": 1,
      "sleep": 7
  }
  ```

#### Kroki:
1. Wybierz metodę `POST` w Postman.
2. Przejdź do zakładki **Authorization** i wybierz `Basic Auth`. Ustaw swoje `username` oraz `password`.
3. Ustaw URL: `http://localhost:8080/moods`.
4. Przejdź do zakładki **Headers** i dodaj:
   ```
   Key: Content-Type 
   Value: application/json
   ```
5. W zakładce **Body** wybierz typ `raw`, a następnie wklej podany JSON.
6. Kliknij `Send`.

---

### **3. Pobieranie wszystkich nastrojów**

#### Szczegóły:
- **Metoda**: `GET`
- **URL**: `http://localhost:8080/`
- **Nagłówki**:
  - Authorization: Basic Auth (ustaw username i password).

#### Kroki:
1. Wybierz metodę `GET` w Postman.
2. Przejdź do zakładki **Authorization** i wybierz `Basic Auth`. Wprowadź swoje dane.
3. Ustaw URL: `http://localhost:8080/`.
4. Kliknij `Send`.

---

### **4. Aktualizacja nastroju**

#### Szczegóły:
- **Metoda**: `PUT`
- **URL**: `http://localhost:8080/moods`
- **Nagłówki**:
  - Content-Type: `application/json`
  - Authorization: Basic Auth (ustaw username i password).
- **Body (JSON)**:
  ```json
  {
      "id": 1,
      "moodText": "Feeling tired",
      "description": "After a long work day",
      "alkochol": 0,
      "sugar": 3,
      "workout": 0,
      "sleep": 5
  }
  ```

#### Kroki:
1. Wybierz metodę `PUT` w Postman.
2. Ustaw dane w zakładce **Authorization** (Tak samo jak przy "Dodawaniu nastroju").
3. Dodaj JSON do **Body**, tak jak w poprzednich krokach.
4. Kliknij `Send`.

---

### **5. Usuwanie nastroju**

#### Szczegóły:
- **Metoda**: `DELETE`
- **URL**: `http://localhost:8080/moods/{id}`
- **Nagłówki**:
  - Authorization: Basic Auth (ustaw username i password).

#### Kroki:
1. Wybierz metodę `DELETE` w Postman.
2. Ustaw dane w zakładce **Authorization**.
3. Ustaw URL: np. `http://localhost:8080/moods/1`.
4. Kliknij `Send`.

---

## Uwagi

- Upewnij się, że każdy request (np. PUT, GET, DELETE) wymaga uwierzytelnienia.
- Konfigurację servera możesz dostosować w pliku `application.properties`.

---

Jeśli masz pytania, skontaktuj się! 😊
