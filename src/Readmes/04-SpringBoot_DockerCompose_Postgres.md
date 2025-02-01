# Configuración Completa de PostgreSQL con Docker y Conexión desde Spring Boot

En esta sección explicaremos de forma detallada cómo configurar PostgreSQL usando Docker Compose y conectar Spring Boot
a la base de datos, incluyendo la configuración de los **drivers de PostgreSQL**.

---

## **1. ¿Qué es Docker?**

Docker es una herramienta que permite empaquetar aplicaciones y sus dependencias en **contenedores**. Un contenedor es
una instancia ligera y portátil que contiene todo lo necesario para que una aplicación funcione.

### **¿Por qué usar Docker?**

- **Portabilidad**: Los contenedores funcionan en cualquier sistema que tenga Docker instalado.
- **Aislamiento**: Cada contenedor tiene su propio entorno, independiente de otros.
- **Reproducibilidad**: Puedes compartir configuraciones para que otros desarrolladores trabajen en el mismo entorno.
- **Facilidad de uso**: Permite montar aplicaciones complejas con unos pocos comandos.

---

## **2. Configuración de PostgreSQL con Docker Compose**

Usaremos **Docker Compose** para simplificar la configuración y ejecución de servicios múltiples.

### **Archivo `docker-compose.yml`**

Crea un archivo llamado `docker-compose.yml` en el directorio raíz de tu proyecto con el siguiente contenido:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:latest
    container_name: pokemon-db
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
      POSTGRES_DB: pokemon_catalog
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

### **Pasos para ejecutar Docker Compose**

1. **Ejecutar los servicios**:
   ```bash
   docker-compose.yml up -d
   ```
    - `-d`: Ejecuta los servicios en segundo plano.

2. **Verificar que el servicio está en ejecución**:
   ```bash
   docker ps
   ```

3. **Detener los servicios**:
   ```bash
   docker-compose.yml down
   ```

---

## **3. Agregar los Drivers de PostgreSQL a Spring Boot**

Los drivers permiten que Spring Boot pueda comunicarse con PostgreSQL. Debes añadir la dependencia en el archivo
`pom.xml` del proyecto.

### **Dependencia para PostgreSQL en `pom.xml`**

```xml

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
</dependency>
```

- **¿Qué hace esta dependencia?**
    - Proporciona las clases necesarias para que Java pueda interactuar con PostgreSQL mediante JDBC (Java Database
      Connectivity).

---

## **4. Configuración del Archivo `application.properties`**

El archivo `application.properties` debe colocarse en la carpeta `src/main/resources` del proyecto Spring Boot.

### **Contenido de `application.properties`**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pokemon_catalog
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

- **Ubicación**:
  ```plaintext
  src/main/resources/application.properties
  ```

### **Explicación**

- **`spring.datasource.url`**: URL de conexión a PostgreSQL (en este caso, el servicio está expuesto en `localhost` en
  el puerto `5432`).
- **`spring.datasource.username` y `spring.datasource.password`**: Credenciales configuradas en `docker-compose.yml`.
- **`spring.jpa.hibernate.ddl-auto`**: Controla cómo Hibernate gestiona las tablas de la base de datos (`update`
  actualiza automáticamente).
- **`spring.jpa.show-sql`**: Muestra las consultas SQL en los logs.

---

## **5. Crear un Endpoint para Interactuar con la Base de Datos**

## ***5.1 prueba el acceso con PSQL***

Recuerda las partes, indicaremos la instrucción psql
-h la dirección donde tendremos la base de datos en este caso localhost
-p el puerto local en el que tenemos el contenedor de docker expuesto y esperando conexión
-U el usuario (el usuario dependerá de la base de datos si hemos creado una directamente en nuestro file)
-d Database el nombre de la base de datos a la que conectar que tendrá creado nuestro usuario
```psql -h localhost -p 5434 -U admin -d cards-manager```

otra forma de acceder a un contenedor docker es mediante la ejecución de un comando en su interior de forma interactiva
la instrucción docker nos ofrece la opcion exec, para ello, elegimos el contenedor y despues la instrucción que se
ejecutará en este.
En este caso psql, pero podría ser bash y ya en el interior del contenedor cualquier otra instrucción.

```docker exec -it my_postgres_container psql -U postgres```

## **6. Prueba del Endpoint**

### **Prueba de respuesta desde la API (`GET`)**

La primera prueba debe ser sencilla un controllers sin ningún tipo de lógica que simplemente responda ok y un String.
Primero anotaremos con "RestoController para informar a Spring que nuestro código va a necesitar poder interactuar con
las peticiones HTTP.
Posteriormente vamos a tener el mapping del request lo que se traduce como la ruta que las peticiones deben cumplir para
acceder a este conjunto de endpoints (métodos del controllers)
Finalmente los métodos van a llevar a una anotación de su tipo los más comunes son.

HTTP :

1. **Get**
2. **POST**
3. **PUT**
4. **PATCH**
5. **DELETE**

```
 
@RestController
@RequestMapping("/cards")
public class CardController {

    @GetMapping
    public ResponseEntity<java.lang.String> getAllCards() {
        return ResponseEntity.ok("YEAH it works");
    }

}
```

@GetMapping para denotar que se trata de obtención de datos.

Utilizaremos el objeto envolvente (wrapper) ResponseEntity el que nos da una serie de funcionalidades específicas para
el trabajo con peticiones http, por ejemplo el .ok("String") nos permite que se devuelva una respuesta de código 200
para el navegador.
con el mensaje especificado.
Si al hacer la petición desde la herramienta postman podemos ver el 200 y el mensaje correcto, significa que todo está
en marcha y correctamente configurado.
```http://localhost:8080/cards```

si todo parece funcionar correctamente podremos proceder.

### **Modelo `Card`**

```java

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
}
```

### **Repositorio `CardRepository`**

```java
public interface CardRepository extends JpaRepository<Card, Long> {

}
```

### **Servicio `CardService`**

```java

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

}
```

### **Controlador `CardController`**

```java

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public Card saveCard(@RequestBody Card card) {
        return cardService.saveCard(card);
    }

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

}
```

---

### **Prueba de creación de cartas (`POST`)**

1. Usa Postman o cualquier cliente HTTP para enviar una solicitud `POST` a `http://localhost:8080/api/cards`.
2. Cuerpo de la solicitud:
   ```json
   {
       "name": "Pikachu",
       "type": "Electric",
       "rarity": "Common",
       "imageUrl": "http://example.com/pikachu.png"
   }
   ```

### **Prueba de consulta de cartas (`GET`)**

1. Envía una solicitud `GET` a `http://localhost:8080/api/cards`.
2. Verifica que se devuelvan todas las cartas guardadas.

---

## **Conclusión**

En esta sección hemos configurado:

1. PostgreSQL usando Docker Compose para simplificar la gestión de contenedores.
2. El archivo `application.properties` en su ubicación correcta para conectar Spring Boot con la base de datos.
3. Incluido los drivers necesarios para interactuar con PostgreSQL.
4. Endpoints para crear y consultar cartas Pokémon.

