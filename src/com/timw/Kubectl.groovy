package com.timw

class Kubectl {

  def steps
  def namespace

  def kubectl = {cmd, namespace -> return this.steps.sh(script: "kubectl $cmd -n $namespace", returnStdout: true)}

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

  def getService(String name) {
    execute("get service ${name}")
  }

  def execute(String command) {
    kubectl command, this.namespace
  }

}
