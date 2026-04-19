pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "tfabinader/op2-assignment-1"
        DOCKER_HUB_CREDS = 'docker-hub-pat'
        SONAR_SERVER_NAME = 'SonarServer'
    }

    tools {
        jdk 'JDK21'
        maven 'Maven3'
        dockerTool 'mac-docker'
    }

    stages {
        stage('Build & Test') {
            steps {
                // Jenkins compiles and tests locally on your Mac to generate reports
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONAR_SERVER_NAME}") {
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_AUTH')]) {
                        sh "mvn sonar:sonar -Dsonar.token=${SONAR_AUTH}"
                    }
                }
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Publish Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
                jacoco(
                    execPattern: '**/target/jacoco.exec',
                    classPattern: '**/target/classes',
                    sourcePattern: '**/src/main/java',
                    inclusionPattern: '**/*.class'
                )
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} ."
                    sh "docker tag ${DOCKER_IMAGE}:${BUILD_NUMBER} ${DOCKER_IMAGE}:latest"
                }
            }
        }

        stage('Run GUI App') {
            steps {
                // The --rm flag ensures the container is deleted when you close the JavaFX window.
                // It routes the display to your Mac's XQuartz server.
                sh "docker run --rm -e DISPLAY=host.docker.internal:0 -e DB_HOST=host.docker.internal ${DOCKER_IMAGE}:${BUILD_NUMBER}"
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withTool('mac-docker') {
                        sh "docker context use default"
                        docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_HUB_CREDS}") {
                            def customImage = docker.image("${DOCKER_IMAGE}:latest")
                            customImage.push()
                        }
                    }
                }
            }
        }
    }
}
