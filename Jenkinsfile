pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                sh 'mvn -version'
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }   
}