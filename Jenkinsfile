pipeline {
  agent any

  environment {
    IMAGE = '113.198.66.77/test_minji/wooridoori-api'
    HARBOR_CRED = 'harbor-robot'
    TAG = "${env.BRANCH_NAME ?: 'main'}-${env.BUILD_NUMBER}"
  }

  stages {
    stage('Build & Push (Jib)') {
      steps {
        withCredentials([usernamePassword(credentialsId: "${HARBOR_CRED}",
                                          usernameVariable: 'HUSER',
                                          passwordVariable: 'HPASS')]) {
          sh '''
            set -e
            chmod +x ./gradlew
            ./gradlew clean build -x test --no-daemon
            ./gradlew jib \
              -Djib.to.image=${IMAGE} \
              -Djib.to.auth.username=$HUSER \
              -Djib.to.auth.password=$HPASS \
              -Djib.to.tags=${TAG},latest \
              -Djib.allowInsecureRegistries=true \
              --no-daemon
          '''
        }
      }
    }
  }

  post {
    success {
      echo "✅ Build success and image pushed!"
    }
    failure {
      echo "❌ Build failed. Check the logs."
    }
  }
}
