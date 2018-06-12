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

    @NonCPS
    void publishAll(String url, String key, String collectionLink, String basedir, String pattern) {
        List files = findFiles(basedir, pattern)

        println files

        def documentClient = new DocumentClient(url, key, null, null)

        files.each {
            def jsonObject = wrapWithBuildInfo(new File(it))
            Document documentDefinition = new Document(jsonObject)
            documentClient.createDocument(collectionLink, documentDefinition, null, false)

            //this.publish(documentClient, collectionLink, jsonObject)
        }
    }

    String wrapWithBuildInfo(File file) {
        Map buildInfo = getBuildInfo()
        buildInfo.put(file.getName(), fileToJson(file))
        JsonOutput.toJson(buildInfo).toString()
    }

    Object fileToJson(File filePath) {
        def jsonSlurper = new JsonSlurper()
        jsonSlurper.parse(filePath)
    }

    def findFiles(String baseDir, String pattern) {

        steps.echo "basedir: ${baseDir}"
        steps.echo "pattern: ${pattern}"

        steps.dir(baseDir) {

          def files = steps.findFiles(glob: pattern)

            echo "count: ${files.size()}"

            files.each {
                echo it
            }
        }

        //def filePath = new hudson.FilePath(Jenkins.getInstance().getComputer(env.NODE_NAME).getChannel(), baseDir)
        //filePath.list(pattern)
        //new FileNameFinder().getFileNames(basedir, pattern)
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
