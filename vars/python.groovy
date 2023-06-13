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

//    environment {
//      NEXUS = credentials('NEXUS')
//    }

    parameters {
      choice(name: 'env', choices: ['dev', 'prod'], description: 'Pick environment')
//      choice(name: 'action', choices: ['apply', 'destroy'], description: 'Pick action')
    }

    stages {
      stage('Code Quality') {
        steps {
          sh 'ls -ltr'
          sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.95.180:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
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
//      stage('Release Application') {
//        when {
//          expression {
//            env.TAG_NAME ==~ ".*"
//          }
//        }
//        steps {
//          sh 'mvn package ; cp target/${component}-1.0.jar ${component}.jar'
//          sh 'echo $TAG_NAME >VERSION'
//          sh 'zip -r ${component}-${TAG_NAME}.zip ${component}.jar VERSION ${schema_dir}'
//          sh 'curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${component}-${TAG_NAME}.zip http://172.31.82.149:8081/repository/${component}/${component}-${TAG_NAME}.zip'
//        }
//      }
    }
    post {
      always {
        cleanWs()
      }
    }
  }  
}