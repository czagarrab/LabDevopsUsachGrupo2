# --- Build stage ---
    FROM maven:3.9-eclipse-temurin-17 AS build
    WORKDIR /app
    COPY pom.xml .
    RUN mvn -q -e -DskipTests dependency:go-offline
    COPY . .
    RUN mvn -q -DskipTests package
    
    # --- Runtime stage ---
    FROM eclipse-temurin:17-jre
    WORKDIR /app
    # Si tu JAR final tiene otro nombre, ajústalo:
    COPY --from=build /app/target/CalculadoraTiempo-0.0.1-SNAPSHOT.jar /app/app.jar
    EXPOSE 8080
    ENV JAVA_OPTS="-Xms128m -Xmx256m"
    ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
    