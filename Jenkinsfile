pipeline {
  agent any

  environment {
    IMAGE = '113.198.66.77/test_minji/wooridoori-api'
    HARBOR_CRED = 'harbor-robot'
    TAG = "${env.BRANCH_NAME ?: 'main'}-${env.BUILD_NUMBER}"
    GRADLE_USER_HOME = "${env.WORKSPACE}/.gradle"
  }

  options {
    timestamps()
    // ansiColor('xterm')  🔥 요 줄 삭제
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Push (Jib)') {
      steps {
        withCredentials([usernamePassword(credentialsId: "${HARBOR_CRED}",
                                          usernameVariable: 'HUSER',
                                          passwordVariable: 'HPASS')]) {
          sh '''
            set -e
            gradle clean build -x test --no-daemon
            gradle jib \
              -Djib.to.image=${IMAGE} \
              -Djib.to.auth.username=$HUSER \
              -Djib.to.auth.password=$HPASS \
              -Djib.to.tags=${TAG},latest \
              -Djib.allowInsecureRegistries=true \
              -Djib.httpTimeout=300000 \
              --no-daemon
          '''
        }
      }
    }
  }

  post {
    success {
      echo "✅ Build & Push completed successfully!"
    }
    failure {
      echo "❌ Build failed. Check the logs above."
    }
  }
}
