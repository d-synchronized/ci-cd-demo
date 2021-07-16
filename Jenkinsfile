node ('worker_node') {
   //Stages
   stage('Source') { 
        //Steps
        bat([script: 'echo ****cloning the code****'])
        git ([branch: 'day-1', url: 'https://github.com/d-synchronized/ci-cd-demo.git'])
   }
   stage('Interactive Input Stage'){
        input message: 'do u wish to continue', ok: 'Yes', parameters: [booleanParam(defaultValue: true, description: 'Is Development?', name: 'PreRelease Flag')]
        input message: 'do u wish to continue', ok: 'Yes', parameters: [choice(choices: ['development,master'], description: 'Choose the branch', name: 'branchInput')]
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
