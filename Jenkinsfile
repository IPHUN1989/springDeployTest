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
                catchError {
                    sh 'mvn clean validate'
                }
            }
        }
        stage('Compile') {
            steps {
                catchError {
                    sh 'mvn compile'
                }
            }
        }
        stage('Test') {
            steps {
                catchError {
                    sh 'mvn test'
                }
            }
        }
        stage('Package') {
            steps {
                catchError {
                    sh 'mvn package'
                }
            }
        }
        stage('Verify') {
            steps {
                catchError {
                    sh 'mvn verify'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('Sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Email notification') {
            steps {
                mail bcc: '',
                body: 'Test',
                cc: '',
                from: '',
                replyTo: '',
                subject: 'Test Jenkinspipeline', to: 'illesp04b@gmail.com'
            }
        }

        stage('Extended notification') {
            steps {
                emailext body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:Check console output at $BUILD_URL to view the results.''',
                subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!',
                to: 'illesp04b@gmail.com'
            }
        }
    }
}
