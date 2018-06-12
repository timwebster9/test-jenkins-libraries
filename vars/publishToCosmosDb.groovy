import com.timw.DocumentPublisher

def call(steps, params) {

  def env = steps.env

  def reportsDir = "${WORKSPACE}/build/gatling/reports"
  def documentPublisher = new DocumentPublisher(steps, params)
  //documentPublisher.publishAll(env.COSMOSDB_URL, env.COSMOSDB_TOKEN_KEY, 'dbs/jenkins/colls/performance-metrics', reportsDir, '**/*.json')
  documentPublisher.publishAll('dbs/jenkins/colls/performance-metrics', reportsDir, '**/*.json')

}
