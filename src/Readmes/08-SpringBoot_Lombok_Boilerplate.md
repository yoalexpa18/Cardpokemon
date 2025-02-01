
# Uso de Lombok en Spring Boot para Reducir Boilerplate

En esta sección explicaremos qué es Lombok, cómo ayuda a eliminar el código repetitivo (boilerplate) y cómo implementarlo en un proyecto Spring Boot. También exploraremos los conceptos relacionados con el boilerplate.

---

## **1. ¿Qué es el boilerplate?**

El boilerplate es el código repetitivo que se encuentra en muchos proyectos de programación. En Java, es común escribir:

- **Constructores**
- **Getters y setters**
- **Métodos `equals()` y `hashCode()`**
- **Métodos `toString()`**

Aunque estos métodos son importantes, escribirlos manualmente puede ser tedioso y propenso a errores.

### **Ejemplo de código con boilerplate**
```java
public class Card {
    private String name;
    private String type;
    private String number;
    private Rarity rarity;

    // Constructor predeterminado
    public Card() {}

    // Constructor con todos los campos
    public Card(String name, String type, String number, Rarity rarity) {
        this.name = name;
        this.type = type;
        this.number = number;
        this.rarity = rarity;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    // Método toString()
    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + ''' +
                ", type='" + type + ''' +
                ", number='" + number + ''' +
                ", rarity=" + rarity +
                '}';
    }
}
```

El código anterior es funcional, pero contiene mucho código repetitivo.

---

## **2. ¿Qué es Lombok?**

Lombok es una biblioteca Java que elimina la necesidad de escribir manualmente el boilerplate. Usa anotaciones para generar automáticamente código como getters, setters, constructores y métodos `toString`, `equals` y `hashCode`.

### **Ventajas de usar Lombok**
1. **Ahorro de tiempo**: Menos tiempo escribiendo código repetitivo.
2. **Código más limpio**: Mejora la legibilidad del código al eliminar ruido innecesario.
3. **Menos errores**: Reduce la posibilidad de errores en la implementación manual de métodos.

---

## **3. Cómo instalar Lombok en un proyecto Spring Boot**

1. **Agregar la dependencia en `pom.xml`**:
   ```xml
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <version>1.18.28</version>
       <scope>provided</scope>
   </dependency>
   ```

2. **Habilitar el plugin de Lombok en tu IDE (esta parte es posible que no sea necesaria)**:
   - Para IntelliJ IDEA:
     - Ve a `File > Settings > Plugins` y busca "Lombok". Instálalo y reinicia el IDE.
     - Asegúrate de habilitar la opción `Enable annotation processing` en `File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors`.
   - Para Eclipse:
     - Descarga el instalador desde [lombok.jar](https://projectlombok.org/download).
     - Ejecuta el `.jar` para integrarlo con Eclipse.

---

## **4. Anotaciones más comunes de Lombok**

### **4.1. `@Getter` y `@Setter`**
Genera automáticamente los métodos `get` y `set` para todos los atributos.

```java
@Getter
@Setter
public class Card {
    private String name;
    private String type;
    private String number;
    private Rarity rarity;
}
```

### **4.2. `@NoArgsConstructor` y `@AllArgsConstructor`**
- **`@NoArgsConstructor`**: Genera un constructor predeterminado sin argumentos.
- **`@AllArgsConstructor`**: Genera un constructor con todos los campos como argumentos.

```java
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private String name;
    private String type;
    private String number;
    private Rarity rarity;
}
```

### **4.3. `@ToString`**
Genera automáticamente el método `toString()`.

```java
@ToString
public class Card {
    private String name;
    private String type;
}
```

### **4.4. `@EqualsAndHashCode`**
Genera los métodos `equals()` y `hashCode()`.

```java
@EqualsAndHashCode
public class Card {
    private String name;
    private String type;
}
```

### **4.5. `@Data`**
Combina las anotaciones:
- `@Getter`
- `@Setter`
- `@ToString`
- `@EqualsAndHashCode`
- `@RequiredArgsConstructor`

```java
@Data
public class Card {
    private String name;
    private String type;
    private String number;
    private Rarity rarity;
}
```

---

## **5. Clase completa usando Lombok**

Aquí está el mismo ejemplo del principio, pero simplificado con Lombok:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    private String number;
    private Rarity rarity;
}
```

---

## **6. Beneficios en un proyecto Spring Boot**

1. **Deserialización limpia**: Al usar Lombok, garantizas que Jackson pueda acceder a los métodos `get` y `set` generados automáticamente.
2. **Código más limpio**: Elimina líneas innecesarias de código, dejando solo lo esencial.
3. **Productividad**: Menos tiempo dedicado a tareas repetitivas.

---

## **Conclusión**

Lombok es una herramienta poderosa para reducir el boilerplate en proyectos Java. Su integración con Spring Boot permite escribir código más limpio, eficiente y legible, sin perder funcionalidad. En los próximos pasos, veremos cómo combinar Lombok con validaciones y manejo de errores.
