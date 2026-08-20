pipeline {
    agent any

    tools {
        maven 'Maven3'   // Jenkins Global Tool Config me is naam se Maven configure karo
        jdk 'JDK17'      // Jenkins Global Tool Config me is naam se JDK configure karo
    }

    environment {
        DOCKER_IMAGE   = "yourdockerhubusername/springboot-app"
        DOCKER_TAG     = "${env.BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: '[github.com](https://github.com/yourusername/your-springboot-repo.git)'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('[index.docker.io](https://index.docker.io/v1/)', 'dockerhub-credentials') {
                        dockerImage.push("${DOCKER_TAG}")
                        dockerImage.push("latest")
                    }
                }
            }
        }

        stage('Cleanup Local Image') {
            steps {
                sh "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG} || true"
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully — build, test aur docker image push ho gayi.'
        }
        failure {
            echo '❌ Pipeline fail ho gayi — logs check karo.'
        }
    }
}
