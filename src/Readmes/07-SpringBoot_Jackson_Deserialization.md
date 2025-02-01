
# Deserialización con Jackson en Spring Boot

En esta sección explicaremos cómo funciona la deserialización de JSON a objetos Java en Spring Boot utilizando Jackson. También aprenderemos los requisitos necesarios para que esta funcionalidad opere correctamente.

---

## **¿Qué es Jackson?**

Jackson es una biblioteca popular para procesar JSON en Java. En Spring Boot, Jackson viene preconfigurado como el procesador JSON predeterminado para serializar y deserializar objetos.

### **Deserialización**

La deserialización es el proceso de convertir datos en formato JSON a un objeto Java.

### **Serialización**

La serialización es el proceso opuesto: convertir un objeto Java a un formato JSON.

---

## **¿Cómo funciona la deserialización en Spring Boot?**

Cuando una solicitud HTTP contiene un cuerpo JSON, Spring Boot utiliza Jackson para convertir ese JSON en un objeto Java. Esto ocurre automáticamente si:

1. **Los nombres de los campos en el JSON coinciden con los nombres de los atributos en la clase Java (case-sensitive).**
2. **La clase Java tiene un constructor vacío o predeterminado.**
3. **La clase Java incluye setters para cada campo que se quiera mapear.**

---

## **Requisitos para la Deserialización**

### **1. Coincidencia de nombres (Case-Sensitive)**

Los nombres de las claves en el JSON deben coincidir exactamente con los nombres de los atributos de la clase Java, incluyendo las mayúsculas y minúsculas.

#### **Ejemplo de JSON:**
```json
{
    "name": "Charizard",
    "type": "Fire",
    "number": "001",
    "rarity": "RARE"
}
```

#### **Clase Java correspondiente:**
```java
public class Card {
    private String name;
    private String type; // Debe ser "type" y no "Type"
    private String number;
    private Rarity rarity;

    // Getters y setters
}
```

### **2. Constructor predeterminado**

Spring necesita un constructor predeterminado (sin argumentos) para poder crear una instancia de la clase antes de rellenar los campos.

#### **Ejemplo:**
```java
public class Card {
    public Card() {
        // Constructor predeterminado
    }
}
```

### **3. Getters y Setters**

Jackson utiliza los métodos `set` para asignar valores a los campos de la clase. Asegúrate de incluir un setter para cada atributo que quieras deserializar.

#### **Ejemplo:**
```java
public class Card {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### **4. Enumeraciones (Enums)**

Si la clase incluye un campo de tipo `enum`, el valor en el JSON debe coincidir exactamente con el nombre del valor en el `enum`.

#### **Ejemplo:**
```java
public enum Rarity {
    COMMON,
    UNCOMMON,
    RARE,
    LEGENDARY
}
```

Si el JSON contiene `"rarity": "RARE"`, Jackson asignará correctamente el valor `RARE` al atributo.

---

## **Estructura Completa: Clase `Card`**

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
}
```

---

## **Errores comunes durante la deserialización**

### **1. Falta de coincidencia de nombres**
Si el JSON contiene una clave que no coincide con el nombre del atributo en la clase, ese campo no se asignará.

#### **JSON:**
```json
{
    "Name": "Charizard", // Mayúscula inicial
    "type": "Fire"
}
```

#### **Solución:**
Asegúrate de que el nombre sea `name` en el JSON.

---

### **2. No se encuentra el setter**
Si un atributo no tiene un método `set`, no se podrá asignar.

#### **Error típico:**
```plaintext
Cannot construct instance of `Card` (no Creators, like default constructor, exist)
```

#### **Solución:**
Asegúrate de incluir todos los setters necesarios.

---

### **3. Valores inválidos en enumeraciones**
Si el JSON contiene un valor que no coincide con ningún valor en el `enum`, se producirá un error.

#### **JSON inválido:**
```json
{
    "rarity": "VERY_RARE" // No existe en el enum
}
```

#### **Solución:**
Valida el JSON antes de enviarlo.

---

## **Conclusión**

La deserialización en Spring Boot con Jackson es una funcionalidad poderosa que automatiza el mapeo de JSON a objetos Java. Para que funcione correctamente:
1. Asegúrate de que los nombres de las claves en el JSON coincidan con los nombres de los atributos.
2. Incluye un constructor predeterminado en la clase.
3. Define setters para todos los campos.
4. Maneja correctamente los `enum` y otros tipos personalizados.

En la próxima sección, abordaremos cómo validar los datos deserializados utilizando `@Valid` y validaciones personalizadas.
