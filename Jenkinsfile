pipeline {
    agent any
    
    stages {
        stage('GitClone') {
        	steps{
        	  git branch: 'develop', credentialsId: 'credentials', url: 'http://de-muc-igovlab-pl.corp.capgemini.com/gitlab/remandal/traffic-count.git'
        	}	
        }
        stage("Upload On s3 Bucket"){
        	steps{
        		sh '''#!/bin/bash
				#sudo apt-get update
				sudo apt-get install -y python3-pip
				chmod 777 scripts/pipeline/release.sh
				bash scripts/pipeline/release.sh'''
        	}
        }
    }
     post {

        failure {
            script {
                mail (to: 'reetesh-kumar.mandal@capgemini.com',
                        subject: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) failed",
                        body: "Please visit ${env.BUILD_URL} for further information"
                );
             }
          }
         success {
                script {
                mail (to: 'reetesh-kumar.mandal@capgemini.com',
                        subject: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) success.",
                        body: "Please visit ${env.BUILD_URL} for further information.",
                );
              }
          }     
    }  
    
}
