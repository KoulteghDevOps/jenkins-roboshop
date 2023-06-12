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

    parameters {
      choice(name: 'env', choices: ['dev', 'prod'], description: 'Pick environment')
//      choice(name: 'action', choices: ['apply', 'destroy'], description: 'Pick action')
    }

    stages {
      stage('Code Quality') {
        steps {
          sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.95.180:9000 -Dsonar.login=admin -Dsonar.password=admin123'
        }
      }
      stage('echo Unit Test Cases') {
        steps {
          sh 'Unit Test Case'
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
    }
    post {
      always {
        cleanWs()
      }
    }
  }  
}