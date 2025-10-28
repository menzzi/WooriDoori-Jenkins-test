pipeline {
  agent any

  environment {
    // 🚨 Harbor 웹이 8080 포트라면 반드시 추가!
    IMAGE = '113.198.66.77:18170/test_minji/wooridoori-api'
    HARBOR_CRED = 'harbor-robot'
    TAG = "${env.BRANCH_NAME ?: 'main'}-${env.BUILD_NUMBER}"
  }

  stages {
    stage('Build & Push (Jib)') {
      steps {
        withCredentials([usernamePassword(credentialsId: "${HARBOR_CRED}", usernameVariable: 'HUSER', passwordVariable: 'HPASS')]) {
          sh '''
            set -e
            chmod +x ./gradlew
            ./gradlew clean build -x test --no-daemon
            ./gradlew jib \
              -Djib.to.image=${IMAGE} \
              -Djib.to.auth.username=$HUSER \
              -Djib.to.auth.password=$HPASS \
              -Djib.to.tags=${TAG},latest \
              -Djib.allowInsecureRegistries=true
          '''
        }
      }
    }
  }
}
