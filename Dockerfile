# ===== Stage 1: Build =====
FROM eclipse-temurin:24-jdk-ubi9-minimal AS build

WORKDIR /app

# Copiar archivos del proyecto
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src ./src

# Dar permisos al wrapper
RUN chmod +x mvnw

# Compilar el proyecto
RUN ./mvnw clean package -DskipTests

# ===== Stage 2: Runtime =====
FROM eclipse-temurin:24-jdk-ubi9-minimal

WORKDIR /app

# Copiar el JAR desde la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]