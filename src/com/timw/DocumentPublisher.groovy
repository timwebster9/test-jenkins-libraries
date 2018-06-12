package com.timw

@Grab('com.microsoft.azure:azure-documentdb:1.15.2')
import com.microsoft.azure.documentdb.Document
import com.microsoft.azure.documentdb.DocumentClient
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import com.cloudbees.groovy.cps.NonCPS

class DocumentPublisher implements Serializable {

    def steps
    def product
    def component
    def environment
    def env

    DocumentPublisher(steps, product, component, environment) {
        this.steps = steps
        this.product = product
        this.component = component
        this.environment = environment
        this.env = steps.env
    }

    /*
    void publish(DocumentClient documentClient, String collectionLink, Object data) {
        Document documentDefinition = new Document(data)
        documentClient.createDocument(collectionLink, documentDefinition, null, false)
    }
    */

    //@NonCPS
    void publishAll(String url, String key, String collectionLink, String baseDir, String pattern) {

        this.steps.echo "before findFiles"
        def files = findFiles(baseDir, pattern)
        this.steps.echo "after findFIles"

        this.steps.echo "Files returned: ${files.size()}"

        def documentClient = new DocumentClient(url, key, null, null)

        files.each {
            def fullPath = "${env.WORKSPACE}/${baseDir}/" + it.path

            this.steps.echo fullPath

            def json = this.steps.readJSON file: fullPath

            def jsonObject = wrapWithBuildInfo(it.name, json)
            Document documentDefinition = new Document(jsonObject)
            documentClient.createDocument(collectionLink, documentDefinition, null, false)
        }
    }

    String wrapWithBuildInfo(fileName, json) {
        Map buildInfo = getBuildInfo()
        buildInfo.put(fileName, json)
        JsonOutput.toJson(buildInfo).toString()
    }

    /*
    Object fileToJson(File filePath) {
        def jsonSlurper = new JsonSlurper()
        jsonSlurper.parse(filePath)
    }
    */

    def findFiles(String baseDir, String pattern) {
        steps.dir(baseDir) {
          def files = steps.findFiles(glob: pattern)
          return files
        }
    }

    Map getBuildInfo() {
        [
                product                      : product,
                component                    : component,
                environment                  : environment,
                branch_name                  : env.BRANCH_NAME,
                build_number                 : env.BUILD_NUMBER,
                build_id                     : env.BUILD_ID,
                build_display_name           : env.BUILD_DISPLAY_NAME,
                job_name                     : env.JOB_NAME,
                job_base_name                : env.JOB_BASE_NAME,
                build_tag                    : env.BUILD_TAG,
                node_name                    : env.NODE_NAME
        ]
    }

}
