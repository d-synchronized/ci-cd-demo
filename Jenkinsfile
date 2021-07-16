node ('worker_node') {
   //Stages
   stage('Source') { 
        //Steps
        bat([script: 'echo ****cloning the code****'])
        git ([branch: 'day-1', url: 'https://github.com/d-synchronized/ci-cd-demo.git'])
   }
   stage('Interactive Input Stage'){
        def isPreRelease = input message: 'Is this Pre Release', ok: 'Yes', parameters: [booleanParam(defaultValue: true, description: 'Is Release?', name: 'releaseType')]
        def branchName = input message: 'Select the branch', ok: 'Yes', parameters: [choice(choices: ['development', 'master'], description: 'Choose the branch', name: 'branchInput')]
        def buildReason = input message: 'Please provide reason for the build', ok: 'Yes', parameters: [string(description: 'Reason for the Build', name: 'buildReason', trim: true)]
        
        echo 'Is This Pre-Release : ' + isPreRelease.releaseType
        echo 'Branch Name Selected is : ' + branchName.branchInput
        echo 'Reason for the build is : ' + buildReason.buildReason
   }
   stage('Build') {
        bat([script: 'echo ****build command goes here****']) 
        bat([script: 'mvn clean install']) 
   }
   properties([
      pipelineTriggers([
         upstream('demo-job, ') //Build after other projects are built
         //,cron('*/15 * * * *')
         ,githubPush() //GitHub hook trigger for GITScm polling
         //,pollSCM('*/15 * * * *') // Very expensive operation
      ])
   ])
}
