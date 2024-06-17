pipeline {
    agent {
        docker {
            image 'maven:latest'
            args '-u root --privileged --net="jenkins" -v $HOME/.m2:/root/.m2'
        }
    }
    environment {
    REPOSITORY = 'iphun/'
    DOCKERHUB_CREDENTIALS=credentials('dockerHub')
    IMAGE_NAME = 'sprindtest'
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
                    sh 'docker build -t ${REPOSITORY}${IMAGE_NAME}:latest -t ${REPOSITORY}${IMAGE_NAME}:${BUILD_NUMBER} .'            }
         }
        }

        stage('Docker Login and Push') {
                steps {
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    sh 'docker push ${REPOSITORY}${IMAGE_NAME}:${BUILD_NUMBER}'
                    sh 'docker push ${REPOSITORY}${IMAGE_NAME}:latest'
                }
        }

        stage('Extended notification') {
            steps {
                emailext (
                    subject: '$DEFAULT_SUBJECT',
                    body: '$DEFAULT_CONTENT',
                    to: '$DEFAULT_RECIPIENTS',
                    recipientProviders: [ requestor() ]
                )
            }
        }
    }
    
}
