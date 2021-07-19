//Groovy Pipeline
node () { //node('worker_node')
   properties([
      parameters([
           booleanParam(defaultValue: true, description: 'Is Release?', name: 'releaseType'),
           gitParameter(branchFilter: 'origin/(.*)', defaultValue: 'master', name: 'BRANCH', type: 'PT_BRANCH'),
           choice(choices: ['development', 'master' , 'day-1'], description: 'Choose the branch', name: 'branchInput'),
           string(description: 'Reason for the Build', name: 'buildReason', trim: true)
      ]),
      disableConcurrentBuilds()
   ])
   
   def repoSSHUrl = 'git@github.com:d-synchronized/ci-cd-demo.git'
   try {
      stage('Source') { 
        //Steps
          bat([script: 'echo ****cloning the code****'])
          //git ([branch: 'day-1', url: 'https://github.com/d-synchronized/ci-cd-demo.git'])
          git branch: "${params.BRANCH}", credentialsId: 'git-ssh', url: repoSSHUrl
      }
      
      
      stage('Update Source') {
          bat "git config user.name 'Dishant Anand'"
          bat "git config user.email d.synchronized@gmail.com"
          sshagent(['git-ssh']) {
             bat "git tag -a v${params.buildReason} -m \"pushing tag v${params.buildReason}\""
             bat "git push ${repoSSHUrl} --tags"
          }
          
     }
   
     stage('Wait Until') {
       echo '***************Wait Until Step******************'
       timeout(time: 15, unit: 'SECONDS') {
          waitUntil {
             try{
                 bat "curl https://www.keycdn.com"
                 return true
             } catch(err) {
                 return false
             }
          }
       }
     }
   
     stage('Parallel Demo') {
        def stepsToRun = [:]
        for(int i = 1; i < 5 ; i++){
            stepsToRun["Step ${i}"] = {
               node {
                  echo 'Step Started'
                  sleep 5
                  echo 'Step Completed'
               }
            }
        }
        //parallel stepsToRun
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
