
# Uso de DTOs (Data Transfer Objects) en Spring Boot con Métodos Reutilizables para la Transformación

En esta sección explicaremos cómo integrar DTOs en un proyecto Spring Boot, enfocándonos en la reutilización y limpieza del código al manejar la transformación entre DTOs y modelos. Exploraremos varias opciones para hacer estas transformaciones más reutilizables.

---

## **1. ¿Qué es un DTO?**

Un **Data Transfer Object (DTO)** es una clase utilizada para transferir datos entre diferentes capas de una aplicación. A diferencia de los modelos de dominio, los DTOs no contienen lógica de negocio; solo almacenan datos.

### **Beneficios de usar DTOs**
1. **Separación de responsabilidades**: Los modelos de dominio representan las entidades del negocio, mientras que los DTOs se centran en la transferencia de datos.
2. **Seguridad**: Permite exponer solo los datos necesarios en las API, protegiendo información sensible.
3. **Flexibilidad**: Los DTOs pueden agregar, omitir o transformar datos del modelo de dominio según las necesidades del cliente.
4. **Evolutividad**: Facilita cambios en el modelo sin afectar las APIs públicas.

---

## **2. Creación del DTO y del Modelo**

### **2.1. DTO `CardDTO`**
El DTO contendrá solo los datos necesarios para la transferencia entre la API y el cliente.

```java
public class CardDTO {
    private String name;
    private String type;
    private String rarity;

    // Getters y setters
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
}
```

### **2.2. Modelo `Card` con Constructor para DTO**
Añadimos un constructor en el modelo que reciba un DTO, permitiendo la transformación directa en el modelo.

```java
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    private String number;
    private Rarity rarity;

    // Constructor predeterminado
    public Card() {}

    // Constructor que recibe un DTO
    public Card(CardDTO cardDTO) {
        this.name = cardDTO.getName();
        this.type = cardDTO.getType();
        this.rarity = Rarity.valueOf(cardDTO.getRarity().toUpperCase());
    }

    // Método para convertir el modelo a DTO
    public CardDTO toDTO() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setName(this.name);
        cardDTO.setType(this.type);
        cardDTO.setRarity(this.rarity.name());
        return cardDTO;
    }

    // Getters y setters
    // ...
}
```

---

## **3. Modificación del Servicio para Usar el Constructor y Métodos de Transformación**

El servicio delega las transformaciones al modelo para reutilizar el código.

```java
@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    // Crear una nueva tarjeta usando el constructor del modelo
    public Card createNewCard(CardDTO cardDTO) {
        Card card = new Card(cardDTO); // Transformación usando el constructor
        return cardRepository.save(card);
    }

    // Obtener todos los modelos y convertirlos a DTO
    public List<CardDTO> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream().map(Card::toDTO).toList(); // Reutiliza el método toDTO
    }
}
```

---

## **4. Modificación del Controlador**

El controlador se mantiene limpio y delega las transformaciones al modelo y al servicio.

```java
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards() {
        List<CardDTO> allCards = cardService.getAllCards();
        return ResponseEntity.ok(allCards);
    }

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO) {
        Card newCard = cardService.createNewCard(cardDTO);
        return ResponseEntity.ok(newCard.toDTO()); // Usar el método toDTO
    }
}
```

---

## **5. Otras Opciones para Transformaciones Reutilizables**

### **5.1. Métodos Estáticos en el DTO**
Puedes usar métodos estáticos en el DTO para realizar las transformaciones.

```java
public class CardDTO {
    public static Card toEntity(CardDTO cardDTO) {
        Card card = new Card();
        card.setName(cardDTO.getName());
        card.setType(cardDTO.getType());
        card.setRarity(Rarity.valueOf(cardDTO.getRarity().toUpperCase()));
        return card;
    }

    public static CardDTO fromEntity(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setName(card.getName());
        cardDTO.setType(card.getType());
        cardDTO.setRarity(card.getRarity().name());
        return cardDTO;
    }
}
```

### **5.2. Uso de MapStruct**
MapStruct genera automáticamente el código para las transformaciones.

**Mapper:**
```java
@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toEntity(CardDTO cardDTO);
    CardDTO toDTO(Card card);
}
```

**Servicio con MapStruct:**
```java
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    public Card createNewCard(CardDTO cardDTO) {
        Card card = cardMapper.toEntity(cardDTO);
        return cardRepository.save(card);
    }

    public List<CardDTO> getAllCards() {
        return cardRepository.findAll()
                             .stream()
                             .map(cardMapper::toDTO)
                             .toList();
    }
}
```

---

## **6. Justificación del Uso de Métodos Reutilizables**

1. **Evitamos duplicación**: Centralizamos la lógica de transformación, reduciendo la duplicación de código.
2. **Facilidad de mantenimiento**: Cualquier cambio en la transformación se realiza en un solo lugar.
3. **Legibilidad**: El código del controlador y del servicio se mantiene limpio y fácil de entender.
4. **Reutilización**: La lógica de transformación puede ser usada en otras partes del proyecto.

---

## **7. Flujo Completo con Métodos Reutilizables**

1. El cliente envía un JSON al controlador.
2. El controlador delega la transformación al modelo o al servicio.
3. El servicio utiliza el constructor o el método `toEntity` para convertir el DTO en un modelo.
4. El repositorio guarda el modelo en la base de datos.
5. El servicio convierte el modelo a DTO usando `toDTO`.
6. El cliente recibe el DTO como respuesta.

---

## **Conclusión**

Centralizar la lógica de transformación entre DTOs y modelos mejora la claridad, reutilización y mantenibilidad del código. Ya sea usando métodos en el modelo, métodos estáticos en los DTOs o herramientas como MapStruct, estas prácticas aseguran una API limpia y eficiente.
