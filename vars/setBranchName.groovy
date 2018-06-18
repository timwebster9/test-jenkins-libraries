def call() {

    def branchName = "${env.BRANCH_NAME}"?.trim()

    if (branchName == 'null') {
        branchName = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD')
    }

    env.BUILD_BRANCH_NAME = branchName.trim().toLowerCase()
    currentBuild.displayName = "#${BUILD_NUMBER}: ${BUILD_BRANCH_NAME}"
}