package com.timw

@Grab('com.microsoft.azure:azure-documentdb:1.15.2')
import com.microsoft.azure.documentdb.Document
import com.microsoft.azure.documentdb.DocumentClient
import groovy.json.JsonOutput
import com.cloudbees.groovy.cps.NonCPS

class DocumentPublisher implements Serializable {

    def steps
    def params
    def env

    DocumentPublisher(steps, params) {
        this.steps = steps
        this.params = params
        this.env = steps.env
    }

    void publishAll(String collectionLink, String baseDir, String pattern) {

        def files = findFiles(baseDir, pattern)
        List documents = new ArrayList()

        files.each {
            def absolutePath = "${baseDir}/" + it.path
            def json = this.steps.readJSON file: absolutePath
            documents.add(wrapWithBuildInfo(it.name, json))
        }

        publish(collectionLink, documents)
    }

    @NonCPS
    private def publish(collectionLink, documents) {

        steps.withCredentials([[$class: 'StringBinding', credentialsId: 'cosmosKey', variable: 'COSMOS_KEY']]) {
            if (steps.env.COSMOS_KEY == null) {
                steps.echo "CosmosDB key not found, skipping performance metrics publishing"
                return
            }

            steps.echo env.COSMOS_KEY
            def cosmosDbUrl = env.COSMOSDB_URL ?: 'https://pipeline-metrics.documents.azure.com/'
            def documentClient = new DocumentClient(cosmosDbUrl, env.COSMOS_KEY, null, null)

            try {
                documents.each {
                    steps.echo "Publishing to collection ${collectionLink}: " + it.toString()
                    documentClient.createDocument(collectionLink, new Document(it), null, false)
                }
            }
            finally {
                documentClient.close()
            }
        }
    }

    String wrapWithBuildInfo(fileName, json) {
        Map buildInfo = getBuildInfo()
        buildInfo.put(fileName, json)
        JsonOutput.toJson(buildInfo).toString()
    }

    def findFiles(String baseDir, String pattern) {
        steps.dir(baseDir) {
            steps.findFiles(glob: pattern)
        }
    }

    Map getBuildInfo() {
        [
            product                      : params.product,
            component                    : params.component,
            environment                  : params.environment,
            branch_name                  : env.BRANCH_NAME,
            build_number                 : env.BUILD_NUMBER,
            build_id                     : env.BUILD_ID,
            build_display_name           : env.BUILD_DISPLAY_NAME,
            job_name                     : env.JOB_NAME,
            job_base_name                : env.JOB_BASE_NAME,
            build_tag                    : env.BUILD_TAG,
            node_name                    : env.NODE_NAME,
            stage_timestamp              : new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC"))
        ]
    }

}
