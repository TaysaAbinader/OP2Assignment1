pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "tfabinader/localizeDemo1"
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
                // 'verify' runs clean, tests, and jacoco:report in one continuous session
                sh 'mvn clean verify \
                    -Dglass.platform=Monocle \
                    -Dmonocle.platform=Headless \
                    -Dprism.order=sw \
                    -Djava.awt.headless=true'
            }
        }

        stage('Publish Results') {
            steps {
                // Publish Unit Tests
                junit '**/target/surefire-reports/*.xml'
                // Publish JaCoCo Coverage
                jacoco()
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
