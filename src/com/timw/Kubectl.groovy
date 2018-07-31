package com.timw

class Kubectl {

  def steps
  def namespace

  def kubectlInNamespace = {cmd, namespace -> return this.steps.sh(script: "kubectl $cmd $namespace", returnStdout: true)}
  def kubectlAsJson = {cmd, namespace -> return this.steps.sh(script: "kubectl $cmd $namespace -o json", returnStdout: true)}

  Kubectl(steps) {
    this.steps = steps
  }

  Kubectl(steps, namespace) {
    this.steps = steps
    this.namespace = namespace
  }

  def apply(String path) {
    execute("apply -f ${path}")
  }

  def delete(String path) {
    execute("delete -f ${path}")
  }

  def getServiceAsJson(String name) {
    executeAsJson("get service ${name}")
  }

  def getNodes() {
    execute("get nodes")
  }

  def executeAsJson(String command) {
    kubectlAsJson command, this.namespace ? "-n ${this.namespace}" : ""
  }

  def execute(String command) {
    kubectlInNamespace command, this.namespace ? "-n ${this.namespace}" : ""
  }

}
