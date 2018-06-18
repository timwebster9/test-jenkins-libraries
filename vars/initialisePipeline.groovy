def call(String appName) {

    def dockerRepo = 'timwebster9'  // should be app name in reality

    setBranchName()
    env.NAMESPACE         = sh returnStdout: true, script: 'cat /var/run/secrets/kubernetes.io/serviceaccount/namespace'
    env.APP_NAME          = appName
    env.IMAGE_REPO        = dockerRepo
    env.IMAGE_BASE_NAME   = "${IMAGE_REPO}/${APP_NAME}"
    env.DEMO_IMAGE_NAME   = "${IMAGE_BASE_NAME}:${BUILD_BRANCH_NAME}"
    env.CI_IMAGE_NAME     = "${DEMO_IMAGE_NAME}-${BUILD_NUMBER}"
    env.CI_SERVICE_NAME   = "${APP_NAME}-${BUILD_BRANCH_NAME}-${BUILD_NUMBER}"
    env.CI_APP_URL        = "http://${CI_SERVICE_NAME}.${NAMESPACE}.svc.cluster.local"
    env.DEMO_SERVICE_NAME = "${APP_NAME}-${BUILD_BRANCH_NAME}"
}