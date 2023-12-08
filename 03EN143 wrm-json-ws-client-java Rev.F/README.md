### Persystent Systems LLC Management API Websocket Java Client

This API was built and tested with Java version 1.8 on 64 bit Ubuntu 14.04.4 LTS.  
`java version "1.8.0_201"`  
`OpenJDK Runtime Environment (IcedTea 2.6.6) (7u101-2.6.6-0ubuntu0.14.04.1)`  
`OpenJDK 64-Bit Server VM (build 24.95-b01, mixed mode)`

It was built in IntelliJ IDEA.

#### Brief overview of tools used  
###### Maven  
"Maven is a build automation tool used primarily for Java projects. The word maven means 'accumulator of knowledge' in Yiddish. Maven addresses two aspects of building software: first, it describes how software is built, and second, it describes its dependencies. Contrary to preceding tools like Apache Ant, it uses conventions for the build procedure, and only exceptions need to be written down. An XML file describes the software project being built, its dependencies on other external modules and components, the build order, directories, and required plug-ins. It comes with pre-defined targets for performing certain well-defined tasks such as compilation of code and its packaging. Maven dynamically downloads Java libraries and Maven plug-ins from one or more repositories such as the Maven 2 Central Repository, and stores them in a local cache. This local cache of downloaded artifacts can also be updated with artifacts created by local projects. Public repositories can also be updated." `wikipedia.org/wiki/Apache_Maven`


#### This is a Maven Project with an included makefile, if you prefer make
If you've never used Maven before, follow these instructions to get up and running and building the 2 projects (1 example project and 1 JAR [the API for inclusion in other projects]).

Note: Maven projects are defined by POM files, which are XML files. There are 2 in this project, one for each of the individual builds.
* pom.xml -- default build for the JAR / SDK


1. open terminal and execute `sudo apt-get update && sudo apt-get install maven`
2. `cd` into the repository for this project.

###### You should also integrate these Maven commands into your IDE.

All of these commands should be executed from the 'project root' directory.

To 'clean' the ./target directory and remove previous .jar files and resource / build artifacts:
* `mvn clean`
  * or `make clean`

To build the JAR/SDK:
* `mvn package -Psdk`
  * or `make`
  * This is a production ready JAR.
  * It does not include com.persistentsystems.exampleclient.*

There are various 'TestClient*' packages provided.  
Please see pom.xml and search for testclient to see what they enable
they can be built using `mvn package -P(pom.xml id tag)

To build the CLI:
* `mvn package -Pcli`
  * or `make cli`

To run CLI:
* `java -jar ./target/SocketClient-v*-jar-with-dependencies.jar`
  * example program located at src/main/java/com/persistentsystems/exampleclient/ExampleClient.java

To build the javadoc:
* `mvn javadoc:javadoc`
  * or `make javadoc`
  * javadocs will be built to `target/site/apidocs/index.html`
  * additionally, `javadoc:javadoc` can be included into other maven build targets to auto-update the javadoc as well.

To install IdeaJ:
* Visit `https://www.jetbrains.com/idea/#chooseYourEdition`
  * Download the correct Community version for your Systems
  * Follow the install instructions

###### To open as IntelliJ (community or professional):
* File > Open (locate root directory) > Open directory
  * Intellij should detect that it is a maven based project and import everything accordingly. However, if it does not, right click on pom.xml and go to Maven > Reimport, Maven > Generate Sources and Update Folders.

###### To open as Eclipse Project (instructions based off 'mars' release):
* File > Import > Maven > Existing Maven Projects > (select root directory)
  * select pom.xml and press finish


Persistent Systems Proprietary Information
"Copyright 2017, Persistent Systems, LLC. All rights reserved. Wave Relay® is a registered
trademark of Persistent Systems, LLC. This document contains proprietary and confidential
information that is the sole property of Persistent Systems, LLC, and this document may not
be excerpted, summarized, copied, distributed, or otherwise published, in whole or in part,
without the prior written permission of Persistent Systems, LLC.”
