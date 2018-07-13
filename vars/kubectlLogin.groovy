def call() {
    sh 'kubectl config set-cluster k8s --server=https://kubernetes.default.svc'
}
