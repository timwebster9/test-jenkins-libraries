package com.timw

class SomeClass {

    public static final String PERF_TEST_REPORT_DIR = 'build/reports/gatling'

    def performanceTest() {
        withDocker('hmcts/moj-gatling-image', '-v $WORKSPACE/src/gatling/conf:/etc/gatling/conf') {
            sh 'gatling.sh -sf src/gatling/simulations -df src/gatling/data -bdf src/gatling/bodies -rf $PERF_TEST_REPORT_DIR -m'
        }
    }
}