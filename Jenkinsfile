pipeline {
    agent {
        docker {
            image 'maven:latest'
            args '-u root --privileged --net="jenkins"'
        }
    }
    stages {
        stage("Env Variables") {
            steps {
                sh "printenv"
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
                mail bcc: '', body: 'Test', cc: '', from: '', replyTo: '', subject: 'Test Jenkinspipeline', to: 'illesp04b@gmail.com'
            }
        }
        }
    }