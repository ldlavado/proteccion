# Proteccion API - Fibonacci Generator

Este proyecto es una API REST desarrollada con **Spring Boot (Java 24)** que permite generar series de Fibonacci con base en la hora del sistema, almacenarlas en una base de datos PostgreSQL, y enviar los resultados por correo electrónico a múltiples destinatarios.

---

## 🚀 Tecnologías

- Java **24**
- Spring Boot
- Maven
- PostgreSQL
- Docker (multi-stage)
- Render (para despliegue en la nube)

---

## ⚙️ Requisitos Previos

- JDK **24** instalado
- Maven 3.8+ (`mvn -v`)
- PostgreSQL (instalado o en Docker)
- Docker (opcional para contenerizar)

---

## 🛠️ Configuración Inicial

### 1. Clonar el proyecto

```bash
git clone https://github.com/tu-usuario/tu-repo.git
cd tu-repo
```

### 2. Crear una base de datos local

```sql
CREATE DATABASE proteccion;
```

### 3. Configura `application.properties` para entorno local

Ubica o crea `src/main/resources/application.properties`:

```properties
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/proteccion
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_email@gmail.com
spring.mail.password=contraseña_app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

app.email.recipients=destino1@gmail.com,destino2@gmail.com
```

> ⚠️ Usa una **contraseña de aplicación** para Gmail, no la contraseña personal.

---

## ▶️ Ejecución local

### 🧪 Opción 1: Maven

```bash
./mvnw clean package
java -jar target/*.jar
```

o directamente:

```bash
./mvnw spring-boot:run
```

### 🐳 Opción 2: Docker

```bash
docker build -t proteccion-api .
docker run -p 8080:8080 proteccion-api
```

---

## 🔐 Autenticación

La API requiere **autenticación básica (Basic Auth)**.

Credenciales por defecto:

| Usuario | Contraseña |
|---------|------------|
| admin   | admin123   |

---

## 🧪 Endpoints principales

### `POST /api/fibonacci`

Genera una nueva serie Fibonacci:

**Request:**
```json
{
  "time": "14:33:22"
}
```

**Response:**
```json
{
  "message": "Fibonacci series generated successfully",
  "data": {
    "time": "14:33:22",
    "seeds": [3, 3],
    "sequence": [1597, 987, 610, ...]
  }
}
```

### `GET /api/fibonacci`

Retorna todas las series guardadas.

---

## 📬 Envío de correos

La serie generada se envía automáticamente a los destinatarios definidos en `app.email.recipients`.

---

## 🐳 Despliegue en Render

Este proyecto incluye un `Dockerfile` multi-stage basado en **Temurin 24 (UBI9)**.  
Puedes desplegarlo directamente desde GitHub:

1. Conecta tu repo a Render
2. Selecciona `Docker` como entorno
3. Configura las variables de entorno necesarias (DB, SMTP, usuarios)
4. Render compilará y ejecutará tu app automáticamente