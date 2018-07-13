def call(String template, List envVars) {
    withEnv(envVars) {
        sh "envsubst < ${template}.tmpl > ${template}.yaml"
        sh "kubectl apply -f ${template}.yaml"
    }
}