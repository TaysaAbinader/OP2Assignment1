# Native arm64 base image
FROM eclipse-temurin:21-jdk

ENV DISPLAY=host.docker.internal:0.0

# Install ONLY the system libraries required by JavaFX to render via X11/XQuartz
RUN apt-get update && \
    apt-get install -y maven libgtk-3-0 libgbm1 libx11-6 libxxf86vm1 libgl1 libgl1-mesa-dri && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Compile the app (skipping UI tests so it doesn't crash during the headless build)
# Maven will now successfully find and download the linux-aarch64 jars!
RUN mvn clean package -DskipTests

RUN mv target/OP2Assignment1-1.0-SNAPSHOT.jar app.jar

# Run the GUI application when Jenkins issues 'docker run'
CMD ["java", "-jar", "app.jar"]
