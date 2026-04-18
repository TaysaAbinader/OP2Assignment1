pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "tfabinader/op2-assignment-1"
        DOCKER_HUB_CREDS = 'docker-hub-pat'
        // This 'SonarServer' must match the name you gave in Jenkins > System
        SONAR_SERVER_NAME = 'SonarServer'
    }

    tools {
        jdk 'JDK25'
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'git@github.com:TaysaAbinader/OP2Assignment1.git',
                    branch: 'main'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // withSonarQubeEnv handles the credentials and URL automatically
                withSonarQubeEnv("${SONAR_SERVER_NAME}") {
                    // Bind the Jenkins credential 'sonar-token' to the variable 'SONAR_AUTH'
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_AUTH')]) {
                        sh "mvn sonar:sonar -Dsonar.token=${SONAR_AUTH}"
                    }
                }
            }
        }

        stage("Quality Gate") {
            steps {
                // Now that the webhook validation is disabled, this will work!
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

        stage('Push to Docker Hub') {
            steps {
                script {
                    sh "docker context use default"
                    withDockerRegistry([credentialsId: "${DOCKER_HUB_CREDS}", url: '']) {
                        sh "docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}"
                        sh "docker push ${DOCKER_IMAGE}:latest"
                    }
                }
            }
        }
    }
}
