# ---------- Build stage ----------
FROM gradle:8-jdk21 AS builder
WORKDIR /home/gradle/project

# Copiar archivos de build
COPY --chown=gradle:gradle . .

# Usar el wrapper si está presente, si no gradle del image
# Ejecuta build (ajusta si tu tarea jar tiene otro nombre)
RUN gradle --no-daemon clean build -x test

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Crear user no-root
RUN addgroup --system app && adduser --system --ingroup app app
USER app

# Copiar jar armado (ajusta el patrón si produces -all o otra ruta)
COPY --from=builder /home/gradle/project/build/libs/*.jar /app/app.jar

# Puerto expuesto por el microservicio (interno)
EXPOSE 8081

# Variables de entorno por defecto (puedes sobrescribir desde docker-compose)
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=prod

# Comando de arranque
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app/app.jar" ]
