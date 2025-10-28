environment {
  IMAGE = '113.198.66.77/test_minji/wooridoori-api'
  HARBOR_CRED = 'harbor-robot'   // 방금 만든 ID
  TAG = "${env.BRANCH_NAME ?: 'main'}-${env.BUILD_NUMBER}"
}

stage('Build & Push (Jib)') {
  steps {
    withCredentials([usernamePassword(credentialsId: "${HARBOR_CRED}", usernameVariable: 'HUSER', passwordVariable: 'HPASS')]) {
      sh '''
        chmod +x gradlew
        ./gradlew clean build -x test
        ./gradlew jib \
          -Djib.to.image=${IMAGE} \
          -Djib.to.auth.username=$HUSER \
          -Djib.to.auth.password=$HPASS \
          -Djib.to.tags=${TAG},latest
      '''
    }
  }
}
