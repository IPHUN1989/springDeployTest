pipeline {
    agent {
        docker {
            image 'maven:latest'
            args '-u root --privileged'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh ' mvn clean package'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('Sonarqube') {
                    sh 'mvnw clean sonar:sonar'
                }
            }
        }
    }
}

