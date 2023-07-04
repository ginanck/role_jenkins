import hudson.model.*
import jenkins.model.*
import groovy.json.JsonOutput

def jsonOutput = []

Jenkins.instance.nodes.each { node ->
    def secret = node.getComputer().getJnlpMac()
    def nodeData = [
        node: node.getNodeName(),
        secret: secret
    ]
    jsonOutput.add(nodeData)
}

def jsonString = JsonOutput.toJson(jsonOutput)
println(jsonString)
