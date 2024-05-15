pipeline {
    agent {
        docker {
            image 'maven:latest'
            args '-u root --privileged --net="jenkins"'
        }
    }
    stages {
        stage('validate') {
            steps {
                sh 'mvn clean validate'
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Verify') {
            steps {
                sh 'mvn verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('Sonarqube') {
                    sh 'mvn sonar:sonar'
                }
                mail bcc: '',
                body: 'Test',
                cc: '',
                from: '',
                replyTo: '',
                subject: 'Test Jenkinspipeline', to: 'illesp04b@gmail.com'
            }
        }
    }
}
