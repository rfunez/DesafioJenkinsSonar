pipeline {
    agent {
        label 'docker-agent'
    }
    
    tools {
          maven 'maven'
    }
    
    environment {
        MYSQL_ROOT_PASSWORD = 'root'
        MYSQL_DATABASE = 'testdb'
        MYSQL_USER = 'raul'
        MYSQL_PASSWORD = 'raul'
        image = 'lts-oraclelinux9'
    }
    
    stages {
        stage ('Startup Database') {
            echo '*****Starting mysql******'
            sh 'docker run --name mysql -e MYSQL_DATABASE=${MYSQL_DATABASE} -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} -e MYSQL_USER=${MYSQL_USER} -e MYSQL_PASSWORD=${MYSQL_PASSWORD} ${image}' 

        }
        stage ('Checkout') {
            steps {
                git branch: 'develop', credentialsId: 'JenkinsSonar', url: 'git@github.com:rfunez/DesafioJenkinsSonar.git'
            }
        }
        stage ('Build') {
            tools {
                jdk 'java8'
            }
            steps {
                catchError(buildResult: 'SUCCESS', message: 'Ha fallado el Build', stageResult: 'FAILURE') {
                          sh 'mvn clean install'
                }
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