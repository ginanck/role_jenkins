# TODO 1
- Test the below Jinja templates which generate correct files
    - jenkins-agent-config.yml.j2
    - jenkins-agent-config-ssh.yaml.j2
    - jenkins-agent-config-jnlp.yaml.j2
- install-configure JNLP agent on Linux
- setup JNLP agent as systemd Service on Linux
- Test all "jenkins-config-*.j2" Jinja templates are generating correct file
- complete "apply-configuration" for the activate "jenkins.yaml" Configuration as Code
- setup Virtual ENV for Master
- setup Virtual ENV for Slaves

# TODO 2
- prepare virtual ENV setup for MAster and Agents
    - create list of python packages
    - create list of npm ENV
    - create list of Java ENV
    - kubectl binary should be place to the agents
    - kustomize binary should be placed
- add create admin user for local db authentication
- add list of users for local db authentication
- update functionality should be added
- maintenance for master should be added
- maintenance for slaves should be added
- upgrade for master should be added
- upgrade for slaves should be added
- upgrade virtual ENV


