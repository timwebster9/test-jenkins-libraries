def call(String name, String credentialsId) {
    withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh 'docker login -u $USERNAME -p $PASSWORD'
        sh "docker  build -t ${name} ."
        sh "docker push ${name}"
    }
}