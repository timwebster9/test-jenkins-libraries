package com.timw

class Kubectl {

  def steps
  def namespace

  def kubectl = {cmd -> return this.steps.sh(script: "kubectl $cmd", returnStdout: true)}
  def kubectlInNamespace = {cmd, namespace -> return this.steps.sh(script: "kubectl $cmd -n $namespace", returnStdout: true)}
  def kubectlAsJson = {cmd, namespace -> return this.steps.sh(script: "kubectl $cmd -n $namespace -o json", returnStdout: true)}

  Kubectl(steps, namespace) {
    this.steps = steps
    this.namespace = namespace
  }

  def apply(String path) {
    executeInNamespace("apply -f ${path}")
  }

  def delete(String path) {
    executeInNamespace("delete -f ${path}")
  }

  def getServiceAsJson(String name) {
    executeAsJson("get service ${name}")
  }

  def getNodes() {
    execute("get nodes")
  }

  def executeAsJson(String command) {
    kubectlAsJson command, this.namespace
  }

  def executeInNamespace(String command) {
    kubectlInNamespace command, this.namespace
  }

  def execute(String command) {
    kubectl command
  }

}
