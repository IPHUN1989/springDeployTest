pipeline {
    agent {
        docker {
            image 'maven:latest'
            args '-u root:113 --privileged --net="jenkins"'
        }
    }
    stages {
        stage('Check user') {
            steps {
                sh ' echo "Current user: $(whoami)"'
            }
        }
        stage('Build') {
            steps {
                sh ' mvn clean package'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('Sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}

