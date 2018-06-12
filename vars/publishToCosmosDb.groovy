@Grab('com.microsoft.azure:azure-documentdb:1.15.2')
import com.timw.DocumentPublisher

def call(steps, params) {

  def env = steps.env

  def documentPublisher = new DocumentPublisher(steps, params.product, params.component, params.environment)
  def reportsDir = "${WORKSPACE}/build/gatling/reports"

  try {
    documentPublisher.publishAll(env.COSMOSDB_URL, env.COSMOSDB_TOKEN_KEY, documentClient, 'dbs/jenkins/colls/performance-metrics', reportsDir, '**/*.json')
  }
  finally {
    documentClient.close()
  }

}
