pipeline {
  agent any

  environment {
    IMAGE = '113.198.66.77/test_minji/wooridoori-api'   // Harbor repository 경로
    HARBOR_CRED = 'harbor-robot'                        // Jenkins Credentials ID
    TAG = "${env.BRANCH_NAME ?: 'main'}-${env.BUILD_NUMBER}"
  }

  stages {
    stage('Checkout') {
      steps {
        echo "🔍 Checking out source code..."
        checkout scm
      }
    }

    stage('Build JAR') {
      steps {
        echo "⚙️ Building Spring Boot application..."
        sh '''
          chmod +x ./gradlew
          ./gradlew clean build -x test --no-daemon
        '''
      }
    }

    stage('Build Docker Image') {
      steps {
        echo "🐳 Building Docker image..."
        script {
          // Docker 빌드 실행
          sh """
            docker build -t ${IMAGE}:${TAG} -f Dockerfile .
          """
        }
      }
    }

    stage('Push to Harbor') {
      steps {
        echo "📦 Pushing Docker image to Harbor..."
        withCredentials([usernamePassword(credentialsId: "${HARBOR_CRED}",
                                          usernameVariable: 'HUSER',
                                          passwordVariable: 'HPASS')]) {
          sh """
            echo $HPASS | docker login 113.198.66.77 -u $HUSER --password-stdin
            docker push ${IMAGE}:${TAG}
            docker tag ${IMAGE}:${TAG} ${IMAGE}:latest
            docker push ${IMAGE}:latest
            docker logout 113.198.66.77
          """
        }
      }
    }
  }

  post {
    success {
      echo "✅ Build and push successful! Image: ${IMAGE}:${TAG}"
    }
    failure {
      echo "❌ Build failed. Check logs for details."
    }
  }
}
