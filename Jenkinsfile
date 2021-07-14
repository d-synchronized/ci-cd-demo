pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                script{
                    bat 'mvn -version'
                    bat 'mvn clean install -DskipTests'	
                }
            }
        }
        stage('Test') {
            steps {
                script{
                   bat 'mvn test'
                }
            }
        }
    }   
}