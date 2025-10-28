pipeline {
  agent any

  environment {
    IMAGE       = '113.198.66.77/test_minji/wooridoori-api'
    HARBOR_CRED = 'harbor-robot'
    TAG         = "${env.BRANCH_NAME ?: 'main'}-${env.BUILD_NUMBER}"
    // Gradle 캐시를 워크스페이스 하위로 고정해서 에이전트 재시작에도 안정
    GRADLE_USER_HOME = "${env.WORKSPACE}/.gradle"
  }

  options {
    timestamps()
    ansiColor('xterm')
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
          // 네트워크 흔들림/일시 오류 대비 재시도
          retry(2) {
            sh '''
              set -euo pipefail

              echo "== Gradle in PATH =="
              which gradle || { echo "ERROR: gradle not found in PATH"; exit 127; }
              gradle --version

              echo "== Clean build =="
              gradle clean build -x test --no-daemon --console=plain

              echo "== Jib build & push =="
              gradle jib \
                -Djib.to.image='${IMAGE}' \
                -Djib.to.auth.username="${HUSER}" \
                -Djib.to.auth.password="${HPASS}" \
                -Djib.to.tags='${TAG},latest' \
                -Djib.allowInsecureRegistries=true \
                --no-daemon --console=plain
            '''
          }
        }
      }
    }
  }

  post {
    success {
      echo "✅ Pushed: ${IMAGE}:${TAG} (and :latest)"
    }
    failure {
      echo "❌ Build failed. Check the first 'ERROR:' line above."
    }
    always {
      archiveArtifacts artifacts: '**/build/libs/*.jar', allowEmptyArchive: true
    }
  }
}
