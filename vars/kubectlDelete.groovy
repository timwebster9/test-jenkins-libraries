def call(String template, List envVars) {
    withEnv(envVars) {
        sh "envsubst < ${template}.tmpl > ${template}.yaml"
        sh 'kubectl config set-cluster k8s --server=https://kubernetes.default.svc'
        sh "kubectl delete -f ${template}.yaml"
    }
}