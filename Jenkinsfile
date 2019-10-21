#!/usr/bin/env groovy

pipeline {

    agent any
    
    stages {
    
        stage('Clean') {
        
            steps {
            
			    withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
				
                    echo 'Cleaning project workspace.'
                    sh 'chmod +x gradlew'
				    sh './gradlew clean'
				}
            }
        }
    }
}