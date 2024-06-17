pipeline {
    agent {
        docker {
            image 'maven:latest'
            args '-u root --privileged --net="jenkins" -v $HOME/.m2:/root/.m2'
        }
    }
    environment {
    imagename = "iphun/sprindtest"
    DOCKERHUB_CREDENTIALS=credentials('dockerHub')
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

        stage('Docker Login and Push') {
                steps {
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    sh "docker tag ${imagename}:${BUILD_NUMBER} ${imagename}:latest"
                    sh 'docker push iphun/sprindtest:$BUILD_NUMBER'
                    sh 'docker push iphun/sprindtest:latest'
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
