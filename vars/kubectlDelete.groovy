def call(String componentName) {
    sh "kubectl delete service ${componentName}"
    sh "kubectl delete deployment ${componentName}"
}