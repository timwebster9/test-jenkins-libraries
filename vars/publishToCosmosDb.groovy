import com.timw.DocumentPublisher

def call(steps, params) {
  def reportsDir = "${WORKSPACE}/build/gatling/reports" // TODO pass from Gatling class constant
  def documentPublisher = new DocumentPublisher(steps, params)
  documentPublisher.publishAll('dbs/jenkins/colls/performance-metrics', reportsDir, '**/*.json')
}
