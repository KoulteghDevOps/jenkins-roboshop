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
      choice(name: 'action', choices: ['apply', 'destroy'], description: 'Pick environment')
      
    }

    stages {
      stage('Code Quality') {
        steps {
          sh 'echo Code Quality'
        }
      }
      stage('Unit Test Cases') {
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