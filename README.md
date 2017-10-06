# kie-server-frontend

This Java program is a proof of concept for communicating with the KIE Server APIs in Red Hat BPM Suite 6. It includes generic Java implementations of KIE Server APIs, as well as a command-line tool that can interact with servers if given a configuration file with valid values.

**WARNING:** This is intended as a proof of concept only, and should by no means be regarded as complete or production-quality. It may contain bugs of varying severity. Use at your own risk!

## Packages
- `org.wildfly.bpms.frontend.api`
  - Implementation of the KIE Server API interfaces found in the [droolsjbpm-integration](https://github.com/kiegroup/droolsjbpm-integration/tree/master/kie-server-parent/kie-server-remote/kie-server-client/src/main/java/org/kie/server/client) repository
- `org.wildfly.bpms.frontend.api.console`
  - Extension of the previous package's APIs such that all parameters can be input as Strings. This allows the APIs to be used with input from a terminal.
- `org.wildfly.bpms.frontend.application`
  - The main application class
- `org.wildfly.bpms.frontend.constants`
  - Constants used in the program
- `org.wildfly.bpms.frontend.exception`
  - Custom exception classes reside here
- `org.wildfly.bpms.frontend.proxy`
  - Classes to allow communicating with a server via either REST or JMS as the communication medium. Special thanks to David Tse from Red Hat for much of this code.
- `org.wildfly.bpms.frontend.util`
  - Utility classes reside here
  
## Usage
1. Build the program using `mvn clean package` (see next section for build requirements)
2. Navigate to `target` directory and run `java -jar bpms-frontend-1.0.2-jar-with-dependencies.jar`
  
## Build Requirements
1. Maven
2. JDK v8 or newer
  
## Acknowledgements
- David Tse from Red Hat, who provided me with both the idea and the initial proxy implementations
