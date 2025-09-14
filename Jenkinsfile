pipeline {
    agent {
        label 'docker-agent'
    }
    
    tools {
          maven 'maven'
    }
    
    environment {
        MYSQL_ROOT_PASSWORD = 'root'
        MYSQL_DATABASE = 'test_db'
        MYSQL_USER = 'test'
        MYSQL_PASSWORD = 'test'
        image = 'mysql:lts-oraclelinux9'
        ECR_REPO = '390403867561.dkr.ecr.eu-south-2.amazonaws.com'
    }

    options {
        buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '3')
    }
 
    
    stages {
        stage ('Startup Mysql Database server') {
            steps {
              echo '******Starting mysql*******'
              sh 'docker run --name mysql -d -p 3306:3306 -e MYSQL_DATABASE=${MYSQL_DATABASE} -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} -e MYSQL_USER=${MYSQL_USER} -e MYSQL_PASSWORD=${MYSQL_PASSWORD} ${image}' 

            }
        }
        stage ('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'JenkinsSonar', url: 'git@github.com:rfunez/DesafioJenkinsSonar.git'
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
        stage('Run integration test') {
            steps {
                sh 'sleep 60'
                sh 'mvn exec:java -Dexec.mainClass="com.example.bank.App"'
            }
        }
        stage ('Sonar Analysis') {
            steps {
                catchError(buildResult: 'SUCCESS', message: 'Failed test', stageResult: 'FAILURE') {
                    withSonarQubeEnv(installationName: 'SonarQube' ,credentialsId: 'SonarQube') {
                                    sh 'mvn clean package sonar:sonar'
                    }
                }
            }
        }
        stage ('Quality Gates') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                       waitForQualityGate abortPipeline: false, credentialsId: 'SonarQube'
                }
            }
        }
        stage ('Build app Docker image') {
            steps {
                 sh '''
                  ls -latr
                  docker build -t app .
                  docker tag app $ECR_REPO/jenkins/app
                '''
            }
        }
        stage ('Login to ECR') {
            steps {
                  withAWS(credentials: 'aws_credentials', profile: 'raul', region: 'eu-south-2') {
                         ecrLogin()
                  }
            }
        }
        stage ('Push app image to AWS ECR') {
            steps {
                  sh 'docker push $ECR_REPO/jenkins/app'
            }
        }
    }
    post {
         always {
            sh 'docker stop mysql ; docker rm mysql'
            sh 'docker rmi ${image}'
            cleanWs()
         }
    }
}