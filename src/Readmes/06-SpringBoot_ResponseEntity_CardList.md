
# Uso de ResponseEntity en Spring Boot y Devolución de una Lista de Cartas

En esta sección, explicaremos cómo usar `ResponseEntity` para manejar respuestas HTTP en Spring Boot y cómo implementar un endpoint para devolver una lista de cartas desde la base de datos.

---

## **¿Qué es ResponseEntity?**

`ResponseEntity` es una clase en Spring Boot que representa la respuesta HTTP enviada desde un controlador. Proporciona control total sobre:

1. **El cuerpo de la respuesta (response body)**.
2. **El código de estado HTTP** (por ejemplo, 200 OK, 404 Not Found).
3. **Los encabezados (headers)** de la respuesta.

### **Ventajas de usar ResponseEntity**
- Permite personalizar completamente la respuesta HTTP.
- Mejora la claridad del código al expresar explícitamente los estados y el contenido de la respuesta.
- Simplifica el manejo de excepciones y errores.

---

## **Métodos más comunes de ResponseEntity**

1. **`ResponseEntity.ok()`**
   - Devuelve una respuesta con el código de estado 200 (OK).
   - Opcionalmente, puedes incluir un cuerpo de respuesta.
   - **Ejemplo:**
     ```java
     return ResponseEntity.ok(cards);
     ```

2. **`ResponseEntity.status()`**
   - Permite configurar un código de estado HTTP específico.
   - Puedes combinarlo con un cuerpo de respuesta usando `.body()`.
   - **Ejemplo:**
     ```java
     return ResponseEntity.status(HttpStatus.CREATED).body(newCard);
     ```

3. **`ResponseEntity.noContent()`**
   - Devuelve una respuesta con el código de estado 204 (No Content).
   - Indica que la solicitud se procesó correctamente, pero no hay contenido en la respuesta.
   - **Ejemplo:**
     ```java
     return ResponseEntity.noContent().build();
     ```

4. **`ResponseEntity.notFound()`**
   - Devuelve una respuesta con el código de estado 404 (Not Found).
   - Indica que el recurso solicitado no existe.
   - **Ejemplo:**
     ```java
     return ResponseEntity.notFound().build();
     ```

5. **`ResponseEntity.badRequest()`**
   - Devuelve una respuesta con el código de estado 400 (Bad Request).
   - Indica que hubo un error en la solicitud del cliente.
   - **Ejemplo:**
     ```java
     return ResponseEntity.badRequest().build();
     ```

6. **`ResponseEntity.headers()`**
   - Permite agregar encabezados HTTP personalizados a la respuesta.
   - **Ejemplo:**
     ```java
     return ResponseEntity.ok()
                          .header("Custom-Header", "CustomValue")
                          .body(cards);
     ```

---

## **Devolviendo una Lista de Cartas con ResponseEntity**

### **Controlador**
El siguiente ejemplo muestra cómo devolver una lista de cartas desde un controlador usando `ResponseEntity`:

```java
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        
        if (cards.isEmpty()) {
            return ResponseEntity.noContent().build(); // Código 204 si no hay cartas
        }

        return ResponseEntity.ok(cards); // Código 200 con la lista de cartas
    }
}
```

### **Servicio**
El servicio utiliza el método `findAll()` del repositorio JPA para obtener todas las cartas:

```java
@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
}
```

### **Repositorio**
El repositorio JPA ya incluye un método predefinido llamado `findAll()`:

```java
public interface CardRepository extends JpaRepository<Card, Long> {}
```

---

## **Pruebas con Postman o Navegador**

1. **Endpoint para obtener todas las cartas:**
   ```plaintext
   GET http://localhost:8080/api/cards
   ```

2. **Respuesta esperada (si hay cartas):**
   - **Código de estado:** 200 OK.
   - **Cuerpo:**
     ```json
     [
         {
             "id": 1,
             "name": "Pikachu",
             "type": "Electric",
             "rarity": "Common",
             "imageUrl": "http://example.com/pikachu.png"
         },
         {
             "id": 2,
             "name": "Charizard",
             "type": "Fire",
             "rarity": "Rare",
             "imageUrl": "http://example.com/charizard.png"
         }
     ]
     ```

3. **Respuesta esperada (si no hay cartas):**
   - **Código de estado:** 204 No Content.
   - **Cuerpo:** Vacío.

---

## **Conclusión**

El uso de `ResponseEntity` permite manejar respuestas HTTP de manera clara y explícita en Spring Boot. Esto mejora la calidad del código y ofrece una experiencia consistente para los clientes que interactúan con la API. En este ejemplo, hemos visto cómo devolver una lista de cartas desde una base de datos con diferentes códigos de estado dependiendo del escenario.

En la próxima sección, veremos cómo añadir validaciones y manejar errores en los endpoints.
