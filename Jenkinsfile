//Groovy Pipeline
node () { //node('worker_node')
   properties([
      pipelineTriggers([
         upstream('demo-job, ') //Build after other projects are built
         //,cron('*/15 * * * *')
         ,githubPush() //GitHub hook trigger for GITScm polling
         //,pollSCM('*/15 * * * *') // Very expensive operation
      ]),
      parameters([
           booleanParam(defaultValue: true, description: 'Is Release?', name: 'releaseType'),
           choice(choices: ['development', 'master'], description: 'Choose the branch', name: 'branchInput'),
           string(description: 'Reason for the Build', name: 'buildReason', trim: true)
      ]),
      //disableConcurrentBuilds()
   ])
   try {
      //Stages
      stage('Pre Run'){
          echo "Is This Pre-Release : ${params.releaseType}"
          echo "Branch Name Selected is : ${params.branchInput}"
          echo "Reason for the build is : ${params.buildReason}"
      }
      stage('Source') { 
        //Steps
          bat([script: 'echo ****cloning the code****'])
          git ([branch: 'day-1', url: 'https://github.com/d-synchronized/ci-cd-demo.git'])
      }
      stage('Interactive Input Stage'){
          echo '*****Will wait for user input now*******'
   
   
          def inputParams = {}
          try{
               timeout(time: 10, unit: 'SECONDS') {
                   inputParams = input message: 'Is this Pre Release', ok: 'Yes', parameters: [
                              booleanParam(defaultValue: true, description: 'Is Release?', name: 'releaseType'),
                              choice(choices: ['development', 'master'], description: 'Choose the branch', name: 'branchInput'),
                              string(description: 'Reason for the Build', name: 'buildReason', trim: true)
                              ] 
               }
          } catch(err){
              echo 'Build sleeping for 5 seconds'
              sleep 20
              echo 'Build resuming after 5 seconds'
              inputParams.releaseType = 'Test-Release'
              inputParams.branchInput = 'Tag V-1.0'
              inputParams.buildReason = 'Demo for Release'     
          }
          echo '*****Will print the input values now*******'
          //echo inputParams
          echo 'Is This Pre-Release : ' + inputParams.releaseType
          echo 'Branch Name Selected is : ' + inputParams.branchInput
          echo 'Reason for the build is : ' + inputParams.buildReason
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
        parallel stepsToRun
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
     
     
     currentBuild.result = 'Build Success'
   } catch(Exception err) {
     //FAILURE
     currentBuild.result = 'Build Failed'
   } finally {
       //post build
       echo '***************************************************'
       echo '***************************************************'
       echo '****POST******BUILD*****ACTION*********START*******'
       echo "Status of Pipeline : ${currentBuild.fullDisplayName}"
       echo "${env.BUILD_URL} has result ${currentBuild.result}"
       echo '****POST******BUILD*****ACTION*********END*********'
       echo '***************************************************'
       echo '***************************************************'
   }
}
