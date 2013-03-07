#!/bin/bash
cd $(dirname $(readlink -f $0))/..
java -classpath config/:lib/* dk.statsbiblioteket.vhsingestmonitor.Watchdog