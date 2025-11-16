# Ansible Role: Jenkins MasterRole Name

=========

Install, configure, and manage Jenkins master/server.

role_jenkins

## Description

This Ansible role is used to install, configure, and manage Jenkins servers. It allows you to set up Jenkins with different types of nodes, LDAP authentication, and role-based access control.

This role handles the complete lifecycle of a Jenkins master server including:

- Fresh installation of JenkinsRequirements

- Version upgrades with backup and cleanup------------

- Plugin management

- Admin user configurationThis role requires Ansible 2.8 or higher.

- Service lifecycle management

Role Variables

## Requirements--------------



- Ansible 2.9 or higherThe following variables in defaults/main.yml can be overridden:

- Java installed on the target system (automatically handled via `role_java` dependency)

- Tomcat or similar servlet container- `jenkins_setup_type`: Specifies the type of Jenkins setup. Default value is 'install'.

- Supported OS: Ubuntu (Focal, Jammy), Debian (Buster, Bullseye), RHEL/CentOS 8+- `jenkins_auth_backend`: Specifies the type of authentication backend. Default value is 'ldap'.

- `jenkins_default_version`: Version of Jenkins to install. Default value is '2.332.3'.

## Role Variables- `jenkins_user`, `jenkins_group`, `jenkins_service`: Specify Jenkins user, group, and service name respectively.

- `jenkins_dir`, `jenkins_sysconfig`, `jenkins_config_dir`, `jenkins_versioned_dir`, `jenkins_versioned_home`: Define various directories related to Jenkins.

### Required Variables- `jenkins_agent_ssh_key`: Specifies the SSH key for Jenkins agent.

- `jenkins_http_url`: The URL for the Jenkins server.

```yaml- `jenkins_timezone`: The timezone for the Jenkins server.

jenkins_version: "2.492.1"- `jenkins_config_authorization_roles`: Configures the authorization roles for Jenkins.

jenkins_http_url: "http://jenkins.internal.guru:8080"- `jenkins_config_ldap`: Configures LDAP settings for Jenkins.

```- `jenkins_config_views`: Configures views for Jenkins.

- `jenkins_config_node_base`, `jenkins_config_node_ssh`, `jenkins_config_node_jnlp`: Defines base, ssh, and jnlp templates for Jenkins nodes.

### Admin Account Configuration- `jenkins_config_nodes_list`: List of nodes to be added to Jenkins. You can add as many nodes as required.

- `jenkins_plugin_list`: List of plugins to be installed on the Jenkins server.

```yaml

jenkins_svc_admin_account_full_name: "Jenkins Admin Account"Dependencies

jenkins_svc_admin_account_username: "jenkins-admin"------------

jenkins_svc_admin_account_password: "Passw0rd1"

jenkins_svc_admin_account_email: "jenkins-admin@example.com"- `role_java`

jenkins_svc_admin_account_token_name: "jenkins-api-token"

```Example Variables

-----------------

### Directory Configuration

```yaml

```yaml---

jenkins_dir: /opt/jenkins# defaults file for role_jenkins

jenkins_versioned_dir: "{{ jenkins_dir }}/versioned/{{ jenkins_version }}_{{ jenkins_alternative_priority }}"

jenkins_versioned_home: "{{ jenkins_versioned_dir }}/home"jenkins_default_version: 2.332.3

jenkins_builds_dir: "{{ jenkins_dir }}/builds"

jenkins_workspaces_dir: "{{ jenkins_dir }}/workspace"jenkins_user: jenkins

```jenkins_group: jenkins

jenkins_service: jenkins

### Servlet Configuration

jenkins_dir: /opt/jenkins

```yamljenkins_sysconfig: /etc/sysconfig/jenkins

jenkins_servlet_systemd_service: tomcatjenkins_config_dir: "{{ jenkins_dir }}/config"

jenkins_servlet_user: tomcatjenkins_versioned_dir: "{{ jenkins_dir }}/versioned/{{ jenkins_default_version }}"

jenkins_servlet_group: tomcatjenkins_versioned_home: "{{ jenkins_versioned_dir }}/home_jenkins"

```jenkins_agent_ssh_key: "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIGtxmmfpmcvkGTcbKkh/0gUvjFZAM64Bl112H+EDf39G jenkins@gkorkmaz"



### Plugin Configurationjenkins_http_url: http://jenkins.nnc.guru:8080

jenkins_timezone: Europe/Helsinki

```yaml

jenkins_default_plugins:jenkins_config_authorization_roles:

  - job-dsl  - name: "admins"

  - ws-cleanup    pattern: ".*"

  - timestamper    assignments:

  - git      - "admins"

  - pipeline-stage-view      - "svc-jenkins"

  - workflow-job    permissions:

  # ... see defaults/main.yml for full list      - "Overall/Administer"

  - name: "application"

jenkins_additional_plugins: []    pattern: ".*"

  # - docker-plugin    assignments:

  # - kubernetes      - "application"

```    permissions:

      - "Overall/Administer"

## Dependencies  - name: "developer"

    pattern: ".*"

- `role_java` - Provides Java runtime environment    assignments:

      - "developer"

## Example Playbook    permissions:

      - "Job/Cancel"

### Simple Installation      - "Overall/Read"

      - "Job/Build"

```yaml      - "SCM/Tag"

---      - "Job/Read"

- name: Install Jenkins Master      - "Job/Workspace"

  hosts: jenkins_master      - "View/Read"

  become: true      - "Credentials/View"

        - "Run/Replay"

  vars:  - name: "ipausers"

    jenkins_version: "2.492.1"    pattern: ".*"

    jenkins_http_url: "http://jenkins.example.com:8080"    assignments:

    jenkins_svc_admin_account_password: "{{ vault_jenkins_admin_password }}"      - "ipausers"

      permissions:

  roles:      - "Overall/Read"

    - role_java      - "SCM/Tag"

    - role_jenkins      - "Job/Read"

```      - "Job/Workspace"

      - "View/Read"

### With Additional Plugins      - "Credentials/View"



```yamljenkins_plugin_list:

---  - javax-activation-api

- name: Install Jenkins Master with Custom Plugins  - javax-mail-api

  hosts: jenkins_master```

  become: true

  Example Inventory

  vars:-----------------

    jenkins_version: "2.492.1"

    jenkins_http_url: "http://jenkins.example.com:8080"```ini

    jenkins_additional_plugins:[master]

      - docker-plugin18.23.62.89 jenkins_config_node_type=master

      - kubernetes```

      - ldap

      - role-strategyExample Playbook

  ----------------

  roles:

    - role_java```yaml

    - role_jenkins- name: Install and configure Jenkins Master

```  hosts: master

  tags:

## Usage    - master

  become: true

### Fresh Installation  become_method: sudo

  roles:

On first run, the role will:    - { role: role_java, tags: java }

1. Install prerequisite packages    - { role: role_jenkins, tags: jenkins }

2. Create Jenkins user and directories```

3. Download and configure Jenkins WAR file

4. Setup Java alternativesLicense

5. Start the servlet container-------

6. Create admin user

7. Install pluginsBSD

8. Create initialization marker

Author Information

### Upgrade------------------



On subsequent runs with a newer `jenkins_version`, the role will:This role was created by Gorkem Inanc Korkmaz. For any queries or further information, please contact me at <gorkeminanckorkmaz@gmail.com>.

1. Check current version
2. Update plugins safely
3. Stop Jenkins service
4. Backup home directory
5. Download new Jenkins version
6. Start with new version
7. Update all plugins
8. Clean up old versions (keeps last 3)

## Related Roles

For Jenkins agents/slaves, use:
- `role_jenkins_agent_linux` - Configure Linux build agents
- `role_jenkins_agent_windows` - Configure Windows build agents

These roles are located in the `roles/` directory.

## Directory Structure

```
role_jenkins/
├── defaults/main.yml          # Default variables
├── tasks/
│   ├── main.yml               # Entry point
│   └── master/
│       ├── main.yml           # Master orchestration
│       ├── install.yml        # Fresh installation workflow
│       ├── upgrade.yml        # Upgrade workflow
│       ├── system_setup.yml   # System prerequisites
│       ├── jenkins_binary.yml # WAR, CLI management
│       ├── service_lifecycle.yml  # Service control
│       ├── admin_bootstrap.yml    # Admin user setup
│       └── plugin_operations.yml  # Plugin management
├── templates/                 # Jinja2 templates
├── files/                     # Static files
├── handlers/main.yml         # Service handlers
└── meta/main.yml            # Role metadata

```

## Example Inventory

```ini
[jenkins_master]
jenkins.example.com

[jenkins_master:vars]
ansible_user=ubuntu
ansible_become=true
```

## Tags

Available tags for selective execution:
- `jenkins` - All Jenkins tasks
- `install` - Fresh installation only
- `upgrade` - Upgrade tasks only

## License

GPL-2.0-or-later

## Author Information

This role was created by Gorkem Inanc Korkmaz.  
Contact: <gorkeminanckorkmaz@gmail.com>

## Contributing

Issues and pull requests are welcome!
