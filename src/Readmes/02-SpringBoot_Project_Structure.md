
# Estructura de Carpetas en un Proyecto Spring Boot

En un proyecto de Spring Boot bien estructurado, cada carpeta tiene una función específica para organizar el código y segregar responsabilidades. A continuación, explicaremos el propósito de cada carpeta principal y su importancia en el diseño de la aplicación.

---

## **1. `controllers`**

### **¿Qué es un controlador?**
Un controlador es responsable de manejar las solicitudes HTTP entrantes y devolver las respuestas correspondientes. En Spring Boot, los controladores están anotados con `@RestController` o `@Controller`.

### **Razón de existir**
- Define los **endpoints** de la API.
- Procesa las solicitudes de los clientes y delega la lógica al servicio correspondiente.
- Es el punto de entrada de la aplicación para interactuar con el cliente (Frontend, Postman, etc.).

### **Ejemplo de responsabilidad**
```java
@RestController
@RequestMapping("/api/cards")
public class CardController {

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }
}
```

### **Importancia de la segregación**
- Mantener los controladores separados facilita la lectura y el mantenimiento del código.
- Los controladores deben enfocarse en manejar la lógica relacionada con HTTP, no en la lógica del negocio.

---

## **2. `services`**

### **¿Qué es un servicio?**
El servicio es la capa donde se implementa la lógica del negocio. Está diseñado para procesar y transformar los datos según las reglas de negocio antes de interactuar con la base de datos o el cliente.

### **Razón de existir**
- Centraliza la lógica del negocio.
- Proporciona una interfaz entre los controladores y los repositorios.
- Reutiliza métodos en diferentes partes del sistema.

### **Ejemplo de responsabilidad**
```java
@Service
public class CardService {
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
}
```

### **Importancia de la segregación**
- Permite modificar la lógica del negocio sin afectar directamente al controlador o al repositorio.
- Facilita la realización de pruebas unitarias.

---

## **3. `repository` (o DAO)**

### **¿Qué es un repositorio?**
El repositorio (o DAO - Data Access Object) es la capa encargada de interactuar directamente con la base de datos. En Spring Boot, se implementa mediante **Spring Data JPA**.

### **Razón de existir**
- Simplifica la interacción con la base de datos utilizando métodos predefinidos como `save()`, `findAll()`, `deleteById()`.
- Aísla la lógica de acceso a datos del resto de la aplicación.

### **Ejemplo de responsabilidad**
```java
public interface CardRepository extends JpaRepository<Card, Long> {}
```

### **Importancia de la segregación**
- Mantiene la lógica de acceso a datos separada de las demás capas.
- Facilita el cambio de tecnología de base de datos (e.g., de PostgreSQL a MySQL).

---

## **4. `dto`**

### **¿Qué es un DTO?**
Un DTO (Data Transfer Object) es una clase utilizada para transferir datos entre las capas de la aplicación. Se usa para encapsular los datos que serán enviados o recibidos en una operación.

### **Razón de existir**
- Evita exponer directamente los modelos de datos (entidades) al cliente.
- Permite estructurar los datos según las necesidades del cliente o del frontend.
- Mejora la seguridad al ocultar campos sensibles.

### **Ejemplo de responsabilidad**
```java
public class CardDTO {
    private String name;
    private String type;
    private String rarity;
}
```

### **Importancia de la segregación**
- Protege las entidades del modelo interno.
- Facilita la adaptación de la API a cambios en los requisitos del cliente.

---

## **5. `model`**

### **¿Qué es un modelo?**
El modelo (o entidad) representa las tablas de la base de datos en forma de clases Java. Cada atributo de la clase corresponde a una columna de la tabla.

### **Razón de existir**
- Define la estructura de los datos almacenados en la base de datos.
- Es la base para las operaciones realizadas por los repositorios.

### **Ejemplo de responsabilidad**
```java
@Entity
public class Card {
    private String name;
    private String type;
}
```

### **Importancia de la segregación**
- Mantiene la definición del modelo de datos independiente de otras capas.
- Facilita la validación y la manipulación de datos.

---

## **Ventajas de esta segregación**
1. **Facilita el mantenimiento**: Los cambios en una capa no afectan directamente a las demás.
2. **Permite escalar**: Agregar nuevas funcionalidades es más sencillo.
3. **Cumple con los principios SOLID**:
   - **S**: Responsabilidad única para cada capa.
   - **O**: Abierto a extensión, cerrado a modificaciones.

---

## **Conclusión**
Separar las responsabilidades en un proyecto Spring Boot no solo mejora la legibilidad, sino que también garantiza que la aplicación sea robusta, escalable y fácil de mantener. En las próximas etapas, seguiremos desarrollando la API REST para implementar nuevas funcionalidades.
