def call(String template, String serviceName, String imageName) {
    withEnv(['IMAGE_NAME=$imageName', 'SERVICE_NAME=serviceName']) {
        sh "envsubst < ${template}.tmpl > ${template}.yaml"
        sh 'kubectl config set-cluster k8s --server=https://kubernetes.default.svc'
        sh "kubectl apply -f ${template}.yaml"
    }
}