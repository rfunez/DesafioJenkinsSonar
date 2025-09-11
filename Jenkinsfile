pipeline {
    agent {
        label 'agent2'
    }
    
    tools {
          maven 'maven'
    }
    
    stages {
        stage ('Checkout') {
            steps {
                git branch: 'feature/sonar', credentialsId: 'JenkinsSonar', url: 'git@github.com:rfunez/DesafioJenkinsSonar.git'
            }
        }
        stage ('Build') {
            tools {
                jdk 'java8'
            }
            steps {
                sh 'mvn clean install'
            }
        }
        stage ('Sonar Analysis') {
            steps {
                withSonarQubeEnv(installationName: 'SonarQube' ,credentialsId: 'SonarQube') {
                    sh 'mvn clean package sonar:sonar'
                }
            }
        }
        stage ('Quality Gates') {
            steps {
                timeout(time: 120, unit: 'SECONDS') {
                       waitForQualityGate abortPipeline: false, credentialsId: 'SonarQube'
                }
            }
        }
    }
    post {
         always {
                cleanWs()
         }
    }
}