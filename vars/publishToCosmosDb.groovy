@Grab('com.microsoft.azure:azure-documentdb:1.15.2')
import com.microsoft.azure.documentdb.DocumentClient
import com.timw.DocumentPublisher

def call(steps, params) {

  def env = steps.env
  def documentClient = new DocumentClient(env.COSMOSDB_URL, env.COSMOSDB_TOKEN_KEY, null, null)
  def documentPublisher = new DocumentPublisher(steps, params.product, params.component, params.environment)
  def reportsDir = '$WORKSPACE/build/gatling/reports'

  try {
    documentPublisher.publishAll(documentClient, 'dbs/jenkins/colls/performance-metrics', reportsDir, '**/*.json')
  }
  finally {
    documentClient.close()
  }

}
