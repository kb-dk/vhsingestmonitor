#!/bin/bash
cd $(dirname $(readlink $0))/..
java -classpath config/:lib/* dk.statsbiblioteket.vhsingestmonitor.Watchdog