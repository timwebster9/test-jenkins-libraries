def call(String deploymentName, String imageName) {
    sh "kubectl set image deployment/${deploymentName} ${deploymentName}=${imageName}"
}
