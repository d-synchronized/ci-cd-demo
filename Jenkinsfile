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
   try {
      stage('Checkout Source Code') { 
          echo '***Checking out source code from repo url ${repoSSHUrl},branchName ${params.BRANCH}***'
          checkout([$class: 'GitSCM', 
                    branches: [[name: "*/${params.BRANCH}"]], 
                    extensions: [], 
                    userRemoteConfigs: [[credentialsId: 'github-credentials', url: "${repoUrl}"]]])
      }
      
      
      stage('Create TAG'){
          bat "git config user.name 'Dishant Anand'"
          bat "git config user.email d.synchronized@gmail.com"
          withCredentials([usernameColonPassword(credentialsId: 'github-account', variable: '')]) {
             bat "git tag -a V-1.0.0 -m \"pushing tag\""
             bat "git push ${repoUrl} --tags"
          }
      }
      
   
   
     stage('Conditional Branching') {
        def isPreRelease = params.releaseType;
        if(releaseType){
           echo 'Build For Production'
        }else{
           echo 'Build For DEV & QA' 
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
