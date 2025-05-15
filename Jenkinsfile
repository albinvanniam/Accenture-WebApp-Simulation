pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                bat './gradlew assemble'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                bat './gradlew test'
            }
        }
    }
}
