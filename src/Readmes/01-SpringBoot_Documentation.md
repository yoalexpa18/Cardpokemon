
# Introducción a Spring Boot y conceptos básicos

## ¿Qué es un framework?

Un **framework** es un conjunto de herramientas, bibliotecas y convenciones que proporciona una estructura predefinida para desarrollar aplicaciones de software. 
Su principal objetivo es facilitar el desarrollo de aplicaciones al proporcionar soluciones estándar para problemas comunes, permitiendo a los desarrolladores concentrarse en la lógica del negocio.

### **Características clave de un framework**
1. **Estructura definida**: Ofrece una arquitectura preestablecida que organiza el código y los componentes del proyecto.
2. **Reutilización de código**: Proporciona componentes reutilizables que ahorran tiempo en el desarrollo.
3. **Inversión de control (IoC)**: El framework se encarga de gestionar el flujo y las dependencias del programa.
4. **Facilita el mantenimiento**: Una estructura bien definida facilita la depuración y el escalado de aplicaciones.

### **Ejemplos de frameworks populares**
- **Frontend**: React, Angular, Vue.js.
- **Backend**: Spring Boot, Django, Laravel.
- **Mobile**: Flutter, React Native.

---

## ¿Qué es Spring Boot?

Spring Boot es un framework basado en Java que permite crear aplicaciones empresariales de forma rápida y sencilla. Está diseñado para reducir la complejidad del desarrollo y la configuración de aplicaciones Spring tradicionales.

### **Características principales**
1. **Configuración automática**: Detecta dependencias y configura componentes automáticamente.
2. **Servidor embebido**: Incluye servidores como Tomcat para ejecutar aplicaciones sin necesidad de configurarlos externamente.
3. **Facilidad de inicio**: Proporciona herramientas como Spring Initializr para comenzar proyectos de manera rápida.
4. **Ecosistema integrado**: Soporte completo para tecnologías como Spring MVC, Spring Data JPA, Spring Security, entre otras.

### **Ventajas**
- Reduce el tiempo de desarrollo.
- Facilita la creación de aplicaciones RESTful y microservicios.
- Compatible con estándares de la industria.

---

## ¿Qué es Spring Initializr?

Spring Initializr es una herramienta que genera la base de un proyecto Spring Boot. Permite configurar el proyecto seleccionando las dependencias, el empaquetado (JAR o WAR) y otras propiedades iniciales.

### **¿Cómo usar Spring Initializr?**
1. Abre la página de [Spring Initializr](https://start.spring.io).
2. Configura las opciones básicas:
   - **Project**: Maven.
   - **Language**: Java.
   - **Spring Boot Version**: Última versión estable.
   - **Dependencies**: Spring Web, Spring Data JPA, PostgreSQL Driver, Spring Boot DevTools.
3. Descarga el proyecto generado.
4. Importa el proyecto en tu IDE preferido.

---

## ¿Qué es Maven?

Maven es una herramienta de gestión de proyectos y dependencias. Simplifica el proceso de compilación, prueba y empaquetado de aplicaciones Java.

### **Características principales**
- Define dependencias en un archivo `pom.xml`.
- Automatiza tareas como la compilación y la generación de artefactos (JAR o WAR).
- Establece una estructura estándar de proyectos.

### **Archivo `pom.xml`**
Ejemplo básico del archivo `pom.xml` generado por Spring Initializr:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>springboot-rest-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>springboot-rest-api</name>
    <description>API REST con Spring Boot</description>
    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starter Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Boot Starter Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- PostgreSQL Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
        </dependency>

        <!-- Spring Boot DevTools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Testing dependencies -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## ¿Qué sigue?

En la siguiente parte, explicaremos la **estructura habitual de carpetas** en un proyecto Spring Boot y cómo separar los controladores, servicios, DAOs y DTOs.
