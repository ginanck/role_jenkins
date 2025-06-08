import jenkins.model.*
import hudson.model.*
import hudson.slaves.*
import hudson.slaves.EnvironmentVariablesNodeProperty.Entry

// Get arguments
String nodeName = args[0]
int numExecutors = args[1].toInteger()
String nodeLabels = args[2]
String nodeDescription = args[3]
String nodeMode = args[4]

// Define node properties
def launcher = new JNLPLauncher(true)
def nodeProperties = []

// Create or update node
Jenkins jenkins = Jenkins.getInstance()
Slave node = jenkins.getNode(nodeName) as Slave

if (node == null) {
    println("Creating new node: ${nodeName}")
    node = new DumbSlave(
        nodeName,
        nodeDescription,
        "/opt/jenkins/agent",  // Remote root directory
        numExecutors.toString(),
        Node.Mode.valueOf(nodeMode.toUpperCase()),
        nodeLabels,
        launcher,
        RetentionStrategy.INSTANCE,
        nodeProperties
    )
    jenkins.addNode(node)
    println("Node '${nodeName}' created successfully")
} else {
    println("Updating existing node: ${nodeName}")
    node.setNodeDescription(nodeDescription)
    node.setNumExecutors(numExecutors)
    node.setLabelString(nodeLabels)
    node.setMode(Node.Mode.valueOf(nodeMode.toUpperCase()))
    jenkins.updateNode(node)
    println("Node '${nodeName}' updated successfully")
}