################## Installs some dependencies that are not available in the publish repositories #####
##
##
######################################################################################################

#!/usr/bin/env bash


abspath=$(cd ${0%/*} && echo $PWD/${0##*/})
BIN_HOME=`dirname $abspath`

mvn install:install-file -Dfile=$BIN_HOME/deps/kafka/kafka-0.7.2.jar -DgroupId=org.apache.kafka -DartifactId=kafka-core -Dpackacing=jar -Dversion=0.7.2 -DpomFile=$BIN_HOME/deps/kafka/pom.xml

