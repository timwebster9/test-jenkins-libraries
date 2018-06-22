def call() {
    def changeUrl = "${CHANGE_URL}"
    def branchName = "${BRANCH_NAME}"

    def repoName = changeUrl.tokenize('/.')[-3]
    def rgName = branchName + '-' + repoName + '-preview'

    return rgName
}