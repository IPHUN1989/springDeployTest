pipeline {
    agent {
        docker {
            image 'maven:latest'
            args '-u root --privileged --net="jenkins" -v $HOME/.m2:/root/.m2'
        }
    }
    environment {
    imagename = "iphun/sprindtest"
    DOCKERHUB_CREDENTIALS=credentials('dockerhub')
    dockerImage = ''
  }
    stages {
        stage('validate') {
            steps {
                catchError {
                    sh 'mvn clean validate -B'
                }
            }
        }
        stage('Compile') {
            steps {
                catchError {
                    sh 'mvn compile -B'
                }
            }
        }
        stage('Test') {
            steps {
                catchError {
                    sh 'mvn test -B'
                }
            }
        }
        stage('Package') {
            steps {
                catchError {
                    sh 'mvn package -B'
                }
            }
        }
        stage('Verify') {
            steps {
                catchError {
                    sh 'mvn verify -B'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('Sonarqube') {
                    sh 'mvn sonar:sonar -B'
                }
            }
        }

        stage('Building image') {
            steps{
                script {
                dockerImage = docker.build imagename
            }
         }
        }

        stage('Docker Push') {
            agent any
                steps {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push iphun/sprindtest:latest'
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
