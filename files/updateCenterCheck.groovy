import jenkins.model.Jenkins

def updateCenter = Jenkins.instance.updateCenter
def sites = updateCenter.sites

// Force an update from the update center
sites.each {
    it.updateDirectlyNow(false)
}

println "Force updated all update centers."
