def call(String oldTag, String newTag, String credentialsId) {
    withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh 'docker login -u $USERNAME -p $PASSWORD'
        sh "docker  tag ${oldTag} ${newTag}"
        sh "docker push ${newTag}"
    }
}