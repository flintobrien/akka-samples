#!/bin/bash

akkahome=$AKKA_HOME
export AKKA_HOME=""
java -Xmx1024M -jar $PWD/misc/sbt-launch-0.7.4.jar "$@"
export AKKA_HOME=akkahome
