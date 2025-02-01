
# API REST para un Catálogo de Cartas Pokémon

En esta sección, comenzaremos a estructurar la API REST que permitirá administrar y consultar un catálogo de cartas Pokémon. Nos centraremos en:

1. Crear la estructura del proyecto en Spring Boot.
2. Diseñar los modelos de datos.
3. Definir los controladores básicos.

---

## **Estructura inicial del proyecto**

### **Capas principales**
La API REST seguirá una estructura modular para separar las responsabilidades:

- **Controller**: Maneja las solicitudes HTTP y define los endpoints.
- **Service**: Contiene la lógica del negocio.
- **Repository (DAO)**: Se encarga de la interacción con la base de datos.
- **DTO**: Clases para transferir datos entre las capas.

### **Estructura de carpetas**
```plaintext
src/main/java/com/example/pokemoncatalog
├── controllers
│   └── CardController.java
├── dto
│   └── CardDTO.java
├── model
│   └── Card.java
├── repository
│   └── CardRepository.java
├── services
│   └── CardService.java
└── PokemonCatalogApplication.java
```

---

## **Modelo de datos inicial**

Creamos una clase `Card` que representará las cartas Pokémon en la base de datos.

### **Clase `Card`**
```java
package com.example.pokemoncatalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String rarity;
    private String imageUrl;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
```

---

## **Repositorio (DAO)**

Creamos una interfaz para acceder a la base de datos.

### **Clase `CardRepository`**
```java
package com.example.pokemoncatalog.repository;

import com.example.pokemoncatalog.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
```

---

## **Servicio**

El servicio contiene la lógica para interactuar con el repositorio y manejar los datos.

### **Clase `CardService`**
```java
package com.example.pokemoncatalog.services;

import com.example.pokemoncatalog.model.Card;
import com.example.pokemoncatalog.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card addCard(Card card) {
        return cardRepository.save(card);
    }
}
```

---

## **Controlador**

El controlador define los endpoints que permitirán interactuar con la API.

### **Clase `CardController`**
```java
package com.example.pokemoncatalog.controllers;

import com.example.pokemoncatalog.model.Card;
import com.example.pokemoncatalog.services.CardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @PostMapping
    public Card addCard(@RequestBody Card card) {
        return cardService.addCard(card);
    }
}
```

---

## **Siguiente paso**

1. Crear la base de datos en PostgreSQL y configurar el archivo `application.properties` para conectarla con el proyecto.
2. Probar los endpoints básicos (`GET /api/cards` y `POST /api/cards`) con herramientas como Postman.

En la próxima sección, integraremos la base de datos y añadiremos más funcionalidades a la API REST.
