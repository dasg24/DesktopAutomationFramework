pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "MAVEN"
    }
    options {
      timeout(time: 5, unit: 'MINUTES') 
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main', credentialsId: 'e8df5fe9-5209-4459-b8e2-b6c72ddb6b65', url: 'https://github.com/dasg24/DesktopAutomationFramework.git'

            }

            
        }
        stage('Compile') {
            steps {
                // Get some code from a GitHub repository
                bat "mvn clean compile"
            }

            
        }
        stage('Run') {
            steps {
                script {
                     
                          bat "mvn test -Dtest=com.das.validation.LoginTest"
                      
                    }
                
                
            }

            
        }
    }
    post {
       
    
        // failure {
        //     //emailext body: 'Test Execution has failed.', subject: 'Jenkins Build Failure', to: 'das.us.dev@gmail.com'
        //         }
            
            always {
                    archiveArtifacts 'target/**'
                     publishHTML([
                              allowMissing: false, 
                              alwaysLinkToLastBuild: false, 
                              keepAll: false, 
                              reportDir: '',
                              reportFiles: 'Extent.html', 
                              reportName: 'ExtentReport', 
                              reportTitles: '', 
                              useWrapperFileDirectly: true])
                
            }
        
    }
            
}
