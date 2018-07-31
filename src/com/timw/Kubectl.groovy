package com.timw

class Kubectl {

  def steps
  def namespace

  Kubectl(namespace) {
    this.steps
    this.namespace = namespace
  }

  def apply(String path) {
    kubectl 'apply -f $path'
  }

  def delete() {

  }

  def kubectl() {
    //def az = { cmd -> return sh(script: "env AZURE_CONFIG_DIR=/opt/jenkins/.azure-$subscription az $cmd", returnStdout: true).trim() }
    return {cmd-> return sh(script: "kubectl $cmd -n $this.namespace", returnStdout: true)}
  }

}
