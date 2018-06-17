def call(String deploymentName, String imageName) {
    sh 'kubectl config set-cluster k8s --server=https://kubernetes.default.svc'
    sh "kubectl set image deployment/${deploymentName} ${deploymentName}=${imageName}"
}
