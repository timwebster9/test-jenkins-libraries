def call(String template) {
    sh "kubectl delete -f ${template}.yaml"
}