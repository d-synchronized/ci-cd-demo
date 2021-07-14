node ('worker_node') {
   //Stages
   stage('Source') { 
        //Steps
        bat([script: 'echo Cloning the code!'])
        git ([branch: 'day-1', url: 'https://github.com/d-synchronized/ci-cd-demo.git'])
      }
}
