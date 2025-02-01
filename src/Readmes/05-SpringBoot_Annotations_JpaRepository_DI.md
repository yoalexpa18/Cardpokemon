
# Anotaciones, JpaRepository e Inyección de Dependencias en Spring Boot

En esta sección explicaremos en detalle las anotaciones utilizadas en Spring Boot, el propósito y funcionamiento de `JpaRepository`, y cómo funciona la inyección de dependencias en Spring.

---

## **1. Anotaciones en Spring Boot**

Spring Boot utiliza anotaciones para simplificar el desarrollo y configurar componentes. Aquí explicamos las más comunes:

### **1.1. `@Service`**
- Marca una clase como un componente de servicio en la capa lógica del negocio.
- Spring registra esta clase como un bean para que pueda ser inyectado en otros componentes.
- Representa la lógica empresarial del sistema.

**Ejemplo:**
```java
@Service
public class CardService {
    // Lógica del negocio aquí
}
```

### **1.2. `@RestController`**
- Combina las anotaciones `@Controller` y `@ResponseBody`.
- Indica que esta clase manejará solicitudes HTTP y devolverá datos directamente en el cuerpo de la respuesta, usualmente en formato JSON.

**Ejemplo:**
```java
@RestController
@RequestMapping("/api/cards")
public class CardController {
    // Métodos que manejan solicitudes HTTP
}
```

### **1.3. `@Repository`**
- Marca una clase como un componente de acceso a datos (DAO).
- Aunque no es necesario declarar explícitamente esta anotación cuando extendemos `JpaRepository`, su uso puede ser útil para semántica y manejo de excepciones específicas de la capa de datos.

**Ejemplo:**
```java
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    // Métodos de acceso a datos
}
```

---

## **2. ¿Qué es `JpaRepository` y cómo funciona?**

`JpaRepository` es una interfaz en Spring Data JPA que extiende `PagingAndSortingRepository`, la cual a su vez extiende `CrudRepository`. Proporciona un conjunto de métodos predefinidos para trabajar con bases de datos.

### **2.1. Métodos principales de `JpaRepository`**
1. **`findAll()`**: Obtiene todos los registros de una tabla.
2. **`findById(ID id)`**: Busca un registro por su identificador.
3. **`save(T entity)`**: Guarda o actualiza un registro.
4. **`deleteById(ID id)`**: Elimina un registro por su identificador.
5. **`count()`**: Devuelve el número total de registros.

**Ejemplo:**
```java
public interface CardRepository extends JpaRepository<Card, Long> {
    // Puedes añadir métodos personalizados aquí
}
```

### **2.2. ¿Cómo funciona internamente?**
1. Spring implementa dinámicamente la interfaz en tiempo de ejecución.
2. Usa el mapeo definido en las entidades JPA (`@Entity`) para generar consultas SQL automáticamente.
3. Soporta consultas personalizadas mediante métodos nombrados o la anotación `@Query`.

**Método personalizado:**
```java
List<Card> findByType(String type);
```

**Consulta personalizada con `@Query`:**
```java
@Query("SELECT c FROM Card c WHERE c.type = :type")
List<Card> findCustomByType(@Param("type") String type);
```

---

## **3. Inyección de Dependencias en Spring Boot**

La inyección de dependencias es un patrón que permite que las clases dependan de objetos externos sin crearlos directamente. Spring se encarga de gestionar y proporcionar estos objetos, conocidos como beans.

### **3.1. Tipos de inyección de dependencias**
1. **Por constructor** (Recomendado):
   - Proporciona las dependencias a través del constructor.
   - Favorece la inmutabilidad y permite inyectar dependencias finales (`final`).

   **Ejemplo:**
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

2. **Por setter:**
   - Las dependencias se inyectan a través de métodos setter.
   - Útil cuando las dependencias son opcionales.

   **Ejemplo:**
   ```java
   @Service
   public class CardService {
       private CardRepository cardRepository;

       @Autowired
       public void setCardRepository(CardRepository cardRepository) {
           this.cardRepository = cardRepository;
       }
   }
   ```

3. **Por campo (no recomendado):**
   - Las dependencias se inyectan directamente en los campos mediante la anotación `@Autowired`.
   - Difícil de probar y no favorece la inmutabilidad.

   **Ejemplo:**
   ```java
   @Service
   public class CardService {
       @Autowired
       private CardRepository cardRepository;
   }
   ```

### **3.2. ¿Cómo funciona internamente?**
1. Spring escanea las clases anotadas (`@Component`, `@Service`, `@Repository`, etc.) y las registra como beans.
2. Cuando una clase necesita una dependencia, Spring busca el bean correspondiente en el contenedor y lo inyecta.

### **3.3. Requisitos para la inyección de dependencias**
- Habilitar el escaneo de componentes mediante `@SpringBootApplication` o `@ComponentScan`.
- Declarar las dependencias como beans (usando anotaciones como `@Component`, `@Service`, etc.).

---

## **4. Ejemplo Completo: Devolución de una Lista de Cartas**

### **Controlador**
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
        return ResponseEntity.ok(cards);
    }
}
```

### **Servicio**
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
```java
public interface CardRepository extends JpaRepository<Card, Long> {}
```

---

## **Conclusión**

- Las anotaciones como `@Service`, `@RestController` y `@Repository` simplifican la configuración y organización del código.
- `JpaRepository` ofrece métodos predefinidos para interactuar con bases de datos y admite consultas personalizadas.
- La inyección de dependencias, especialmente por constructor, mejora la claridad, la testabilidad y la mantenibilidad del código.

En la próxima sección, aprenderemos a manejar validaciones y errores en nuestros endpoints.
