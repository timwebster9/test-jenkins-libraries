package com.timw

class Kubectl {

  def steps
  def namespace

  def kubectl = {cmd-> return sh(script: "kubectl $cmd -n ${this.namespace}", returnStdout: true)}

  Kubectl(namespace) {
    this.steps
    this.namespace = namespace
  }

  def apply(String path) {
    kubectl 'apply -f $path'
  }

  def delete() {

  }

}
