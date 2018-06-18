def call() {

    def branchName = "${BRANCH_NAME}"

    if (!branchName?.trim()) {
        env.BRANCH_NAME = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim().toLowerCase()
    }

    currentBuild.displayName = "#${BUILD_NUMBER}: ${BRANCH_NAME}"
}