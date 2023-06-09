def call() {
  pipeline {
    agent {
      node{
        label 'workstation'
      }
    }

    options {
      ansiColor('xterm')
    }

    environment {
      NEXUS = credentials('NEXUS')
    }

    parameters {
      choice(name: 'env', choices: ['dev', 'prod'], description: 'Pick environment')
//      choice(name: 'action', choices: ['apply', 'destroy'], description: 'Pick action')
    }

    stages {
      stage('Code Quality') {
        steps {
//          sh 'ls -ltr'
//          sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.95.180:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
          sh 'echo Code Quallity'
        }
      }
      stage('echo Unit Test Cases') {
        steps {
//          sh 'echo Unit Test Case'
          sh 'python3.11 -m unittest'
        }
      }
      stage('CheckMarX SAST Scan') {
        steps {
          sh 'echo CheckMarx Scan'
        }
      }
      stage('CheckMarx SCA Scan') {
        steps {
          sh 'echo CheckMarx SCA Scan'
        }
      }
      stage('Release Application') {
        when {
          expression {
            env.TAG_NAME ==~ ".*"
          }
        }
        steps {
          sh 'echo $TAG_NAME >VERSION'
          //sh 'zip -r ${component}-${TAG_NAME}.zip *.py *.ini *.txt VERSION ${schema_dir}'
          //sh 'curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${component}-${TAG_NAME}.zip http://172.31.18.217:8081/repository/${component}/${component}-${TAG_NAME}.zip'
          sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 878756956432.dkr.ecr.us-east-1.amazonaws.com'
          sh 'docker build -t 878756956432.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME} .'
          sh 'docker push 878756956432.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME} .'
        }
      }
    }
    post {
      always {
        cleanWs()
      }
    }
  }  
}