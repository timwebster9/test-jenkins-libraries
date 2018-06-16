def call() {
    env.BRANCH_NAME = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
    currentBuild.displayName = "#${BUILD_NUMBER}: ${BRANCH_NAME}"
}