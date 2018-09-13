package com.timw

import groovy.json.JsonOutput

class GithubAPI {

    private static final String API_URL = "https://api.github.com/repos/hmcts"

    def steps

    GithubAPI(steps) {
        this.steps = steps
    }

    def addLabel(project, issueNumber, labels) {

        body = JsonOutput.toJson(labels)

        response = this.steps.httpRequest(httpMode: 'POST',
                                          acceptType: 'APPLICATION_JSON',
                                          contentType: 'APPLICATION_JSON',
                                          url: "${API_URL}/${project}/issues/${issueNumber}/labels",
                                          requestBody: "${body}",
                                          consoleLogResponseBody: true,
                                          validResponseCodes: '200')
    }

}
