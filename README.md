Role Name
=========

role_jenkins

This Ansible role is used to install, configure, and manage Jenkins servers. It allows you to set up Jenkins with different types of nodes, LDAP authentication, and role-based access control.

Requirements
------------

This role requires Ansible 2.8 or higher.

Role Variables
--------------

The following variables in defaults/main.yml can be overridden:

- `jenkins_setup_type`: Specifies the type of Jenkins setup. Default value is 'install'.
- `jenkins_auth_backend`: Specifies the type of authentication backend. Default value is 'ldap'.
- `jenkins_default_version`: Version of Jenkins to install. Default value is '2.332.3'.
- `jenkins_user`, `jenkins_group`, `jenkins_service`: Specify Jenkins user, group, and service name respectively.
- `jenkins_dir`, `jenkins_sysconfig`, `jenkins_config_dir`, `jenkins_versioned_dir`, `jenkins_versioned_home`: Define various directories related to Jenkins.
- `jenkins_agent_ssh_key`: Specifies the SSH key for Jenkins agent.
- `jenkins_http_url`: The URL for the Jenkins server.
- `jenkins_timezone`: The timezone for the Jenkins server.
- `jenkins_config_authorization_roles`: Configures the authorization roles for Jenkins.
- `jenkins_config_ldap`: Configures LDAP settings for Jenkins.
- `jenkins_config_views`: Configures views for Jenkins.
- `jenkins_config_node_base`, `jenkins_config_node_ssh`, `jenkins_config_node_jnlp`: Defines base, ssh, and jnlp templates for Jenkins nodes.
- `jenkins_config_nodes_list`: List of nodes to be added to Jenkins. You can add as many nodes as required.
- `jenkins_plugin_list`: List of plugins to be installed on the Jenkins server.

Dependencies
------------

- `role_java`

Example Variables
-----------------

```yaml
---
# defaults file for role_jenkins

jenkins_default_version: 2.332.3

jenkins_user: jenkins
jenkins_group: jenkins
jenkins_service: jenkins

jenkins_dir: /opt/jenkins
jenkins_sysconfig: /etc/sysconfig/jenkins
jenkins_config_dir: "{{ jenkins_dir }}/config"
jenkins_versioned_dir: "{{ jenkins_dir }}/versioned/{{ jenkins_default_version }}"
jenkins_versioned_home: "{{ jenkins_versioned_dir }}/home_jenkins"
jenkins_agent_ssh_key: "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIGtxmmfpmcvkGTcbKkh/0gUvjFZAM64Bl112H+EDf39G jenkins@gkorkmaz"

jenkins_http_url: http://jenkins.nnc.guru:8080
jenkins_timezone: Europe/Helsinki

jenkins_config_authorization_roles:
  - name: "admins"
    pattern: ".*"
    assignments:
      - "admins"
      - "svc-jenkins"
    permissions:
      - "Overall/Administer"
  - name: "application"
    pattern: ".*"
    assignments:
      - "application"
    permissions:
      - "Overall/Administer"
  - name: "developer"
    pattern: ".*"
    assignments:
      - "developer"
    permissions:
      - "Job/Cancel"
      - "Overall/Read"
      - "Job/Build"
      - "SCM/Tag"
      - "Job/Read"
      - "Job/Workspace"
      - "View/Read"
      - "Credentials/View"
      - "Run/Replay"
  - name: "ipausers"
    pattern: ".*"
    assignments:
      - "ipausers"
    permissions:
      - "Overall/Read"
      - "SCM/Tag"
      - "Job/Read"
      - "Job/Workspace"
      - "View/Read"
      - "Credentials/View"

jenkins_plugin_list:
  - javax-activation-api
  - javax-mail-api
```

Example Inventory
-----------------

```ini
[master]
18.23.62.89 jenkins_config_node_type=master
```

Example Playbook
----------------

```yaml
- name: Install and configure Jenkins Master
  hosts: master
  tags:
    - master
  become: true
  become_method: sudo
  roles:
    - { role: role_java, tags: java }
    - { role: role_jenkins, tags: jenkins }
```

License
-------

BSD

Author Information
------------------

This role was created by Gorkem Inanc Korkmaz. For any queries or further information, please contact me at <gorkeminanckorkmaz@gmail.com>.
