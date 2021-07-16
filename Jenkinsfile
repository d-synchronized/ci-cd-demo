//Groovy Pipeline
node ('worker_node') {
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
      ])
   ])
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
   
   
        def inputParams
        timeout(time: 30, unit: 'SECONDS') {
           inputParams = input message: 'Is this Pre Release', ok: 'Yes', parameters: [
                            booleanParam(defaultValue: true, description: 'Is Release?', name: 'releaseType'),
                            choice(choices: ['development', 'master'], description: 'Choose the branch', name: 'branchInput'),
                            string(description: 'Reason for the Build', name: 'buildReason', trim: true)
                         ] 
        }
        echo '*****Will print the input values now*******'
        echo inputParams
        echo 'Is This Pre-Release : ' + inputParams.releaseType
        echo 'Branch Name Selected is : ' + inputParams.branchInput
        echo 'Reason for the build is : ' + inputParams.buildReason
   }
   stage('Build') {
        bat([script: 'echo ****build command goes here****']) 
        bat([script: 'mvn clean install']) 
   }
}
