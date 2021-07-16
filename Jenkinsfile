node ('worker_node') {
   //Stages
   stage('Source') { 
        //Steps
        bat([script: 'echo ****cloning the code****'])
        git ([branch: 'day-1', url: 'https://github.com/d-synchronized/ci-cd-demo.git'])
   }
   stage('Build') {
        bat([script: 'echo ****build command goes here****']) 
        bat([script: 'mvn clean install']) 
   }
   properties([pipelineTriggers([upstream('demo-job, ')])])
}
