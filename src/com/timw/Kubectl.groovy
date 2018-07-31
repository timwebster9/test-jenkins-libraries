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
    kubectl 'apply -f $path', this.namespace
  }

  def delete() {

  }

  def kubectl() {
    return
  }

}
