def call() {
    echo sh(returnStdout: true, script: 'env')
}