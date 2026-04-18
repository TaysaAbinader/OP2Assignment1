# Use a known-good OpenJDK base image
FROM --platform=linux/amd64 eclipse-temurin:21-jdk

# Optional: set up display (for GUI forwarding)
ENV DISPLAY=host.docker.internal:0.0

# Install dependencies for GUI + Maven build + libxxf86vm1 and xvfb for TestFX
RUN apt-get update && \
    apt-get install -y maven wget unzip libgtk-3-0 libgbm1 libx11-6 libxxf86vm1 xvfb && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Download JavaFX SDK 21 (Since we used --platform=linux/amd64, this x64 SDK will now work perfectly)
RUN wget https://download2.gluonhq.com/openjfx/21/openjfx-21_linux-x64_bin-sdk.zip -O /tmp/openjfx.zip && \
    unzip /tmp/openjfx.zip -d /opt && \
    rm /tmp/openjfx.zip

WORKDIR /app

# Copy project files from your Mac into the container
COPY pom.xml .
COPY src ./src

# Build the shaded JAR
RUN mvn clean package -DskipTests

# List target folder to check JAR
RUN ls -l target
RUN mv target/OP2Assignment1-1.0-SNAPSHOT.jar app.jar

# Run tests using xvfb (fake display) AND override the DB host to point to your Mac
#RUN xvfb-run mvn test -X jacoco:report -Ddb.url=jdbc:mariadb://host.docker.internal:3306/your_database_name
