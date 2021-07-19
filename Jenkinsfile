//Groovy Pipeline
node () { //node('worker_node')
   properties([
      parameters([
           gitParameter(branchFilter: 'origin/(.*)', defaultValue: 'master', name: 'BRANCH', type: 'PT_BRANCH'),
           choice(choices: ['DEV', 'QA' , 'PROD'], description: 'Choose the branch', name: 'ENVIRONMENT'),
      ]),
      disableConcurrentBuilds()
   ])
   
   def repoUrl = 'https://github.com/d-synchronized/ci-cd-demo.git'
   def createTag = false;
   try {
      stage('Checkout Source Code') { 
          echo "***Checking out source code from repo url ${repoUrl},branchName ${params.BRANCH}***"
          checkout([$class: 'GitSCM', 
                    branches: [[name: "*/${params.BRANCH}"]], 
                    extensions: [], 
                    userRemoteConfigs: [[credentialsId: 'github-credentials', url: "${repoUrl}"]]])
      }
      
      
      stage('Create TAG'){
          createTag = "${params.ENVIRONMENT}" == 'PROD' ? true : false;
          
          if(createTag){
             //Drop SNAPSHOT
             bat "mvn versions:set -DremoveSnapshot -DgenerateBackupPoms=false"
          
             bat "git config user.name 'Dishant Anand'"
             bat "git config user.email d.synchronized@gmail.com"
             withCredentials([usernamePassword(credentialsId: 'github-account', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                VERSION = sh(returnStdout:  true, script: 'git describe --abbrev=0 --tags').trim()
                VERSION_BITS=VERSION.tokenize(".")
                VNUM1="${VERSION_BITS[0]}"
                VNUM2="${VERSION_BITS[1]}"
                VNUM3="${VERSION_BITS[2]}"
                VNUM3= VNUM3?.isInteger() ? VNUM3.toInteger() + 1 : null
                NEW_TAG="${VNUM1}.${VNUM2}.${VNUM3}"
                echo "***Upgrading TAG from ${VERSION} to ${NEW_TAG}***"
                bat "git tag -a ${NEW_TAG} -m \"pushing tag ${NEW_TAG}\""
                echo "***Pushing the TAG ${NEW_TAG} to the repository***"
                bat "git push https://${env.GIT_USERNAME}:${env.GIT_PASSWORD}@github.com/d-synchronized/ci-cd-demo.git --tags"
                echo "***TAG CREATION COMPLETE***"
             }//with credentials ends here
          } else {
              echo "***TAG creation skipped for the environment ${params.ENVIRONMENT}***"
          }
          
      }
      
   
     stage('Build') {
          bat([script: 'echo ****build command goes here****']) 
          bat([script: 'mvn clean install']) 
          milestone label: 'After Build', ordinal: 1
     }
     
     
     currentBuild.result = 'SUCCESS'
   } catch(Exception err) {
     //FAILURE
     echo 'Some error occurred during the build ' + err
     currentBuild.result = 'FALIURE'
   } finally {
       //deleteDir()
       //post build
       echo '***************************************************'
       echo '***************************************************'
       echo '****POST******BUILD*****ACTION*********START*******'
       
       //mail to: 'd.synchronized@gmail.com', cc: 'vision4cloud@gmail.com,d.xcption13@gmail.com', bcc: 'slayer4cloud@gmail.com', 
       //     body: "Status for ${env.JOB_NAME} (${env.JOB_URL}) is ${currentBuild.result}", 
       //     subject: "Status of pipeline : ${currentBuild.fullDisplayName}"
            
       emailext attachLog: true, 
                body: 'Status for ${env.JOB_NAME} (${env.JOB_URL}) is ${currentBuild.result}', 
                subject: 'Status of pipeline : ${currentBuild.fullDisplayName',
                to: 'd.synchronized@gmail.com,d.xcption13@gmail.com'
       
       echo '****POST******BUILD*****ACTION*********END*********'
       echo '***************************************************'
       echo '***************************************************'
   }
}
