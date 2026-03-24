pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "tfabinader/op2-assignment-1"
        DOCKER_HUB_CREDS = 'docker-hub-pat'
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
                // Keep the combined clean verify to ensure agent runs
                sh 'mvn clean verify'
            }
        }

        stage('Publish Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'

                // Explicitly define patterns so Jenkins finds the files
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
