# Makefile for the main API JAR
# just a simple wrapper around maven
#sdk


VERSION = $$(cat pom.xml | awk '/<version>/ { print $1; exit; }' | grep -o '[0-9]*\.[0-9]*\.[0-9]*')

default:
	mvn package -Psdk

# test
test:
	mvn test

# cleans dir
clean:
	mvn clean

# makes testclient 2 async version
testclient2async:
	mvn package -Ptestclient2async

# makes testclient 2 blocking version
testclient2blocking:
	mvn package -Ptestclient2blocking

# makes testclient 3 async
testclient3async:
	mvn package -Ptestclient3async

# makes testclient3
testclient3blocking:
	mvn package -Ptestclient3blocking

# makes testclient3
testclient3asyncnetwork:
	mvn package -Ptestclient3asyncnetwork

testclient3blockingnetwork:
	mvn package -Ptestclient3blockingnetwork

testclient4blockingfirmware:
	mvn package -Ptestclient4blockingfirmware

testclient5blocking:
	mvn package -Ptestclient5blocking

firmware:
	mvn package -Pfirmwaretest

testsuspend:
	mvn package -Ptestsuspend

testclientiperf:
	mvn package -Ptestclientiperf

clang:
	mvn package -Ptestclientclang

# make cli client
cli:
	mvn package -Pcli

# make javadoc
javadoc:
	mvn -Psdk javadoc:javadoc

# makes a release for customers to use, excludes private WR dirs
# always run this command, do NOT manually make a release.
release:
	zip -9 -r --exclude=**/internal/* wrm-json-ws-client-java-customer-$(VERSION).zip Makefile pom.xml README.md GRADLE_README.md src/

install:
	mvn package -Psdk
	mvn install:install-file -Dfile=target/WrWebSocketClient-v$(VERSION).jar -DgroupId=com.persistentsystems.wrwebsocketclient -DartifactId=wrwebsocketclient -Dversion=$(VERSION) -Dpackaging=jar
