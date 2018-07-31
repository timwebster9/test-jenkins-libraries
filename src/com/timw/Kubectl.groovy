package com.timw

class Kubectl {

  def steps
  def namespace

  def kubectl = {cmd, namespace -> return sh(script: "kubectl $cmd -n $namespace", returnStdout: true)}

  Kubectl(namespace) {
    this.steps
    this.namespace = namespace
  }

  def apply(String path) {
    kubectl 'apply -f $path', this.namespace
  }

  def delete() {

  }

}
