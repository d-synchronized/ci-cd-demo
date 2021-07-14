pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                script{
                    sh 'mvn -version'
                    sh 'mvn clean install -DskipTests'	
                }
            }
        }
        stage('Test') {
            steps {
                script{
                   sh 'mvn test'
                }
            }
        }
    }   
}