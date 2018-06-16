def call() {
    return sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
}