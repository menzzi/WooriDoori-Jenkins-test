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
        withCredentials([usernamePassword(credentialsId: "${HARBOR_CRED}", usernameVariable: 'HUSER', passwordVariable: 'HPASS')]) {
          sh '''
            # ✅ SSL 검증 문제 회피용 설정 추가
            export GRADLE_OPTS="-Djavax.net.ssl.trustStoreType=JKS -Dcom.sun.net.ssl.checkRevocation=false"

            # ✅ gradlew 실행 전 권한 부여
            chmod +x gradlew

            # ✅ Gradle wrapper 대신 로컬 Gradle을 사용하고 싶으면 아래 두 줄 변경 가능
            ./gradlew clean build -x test
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
