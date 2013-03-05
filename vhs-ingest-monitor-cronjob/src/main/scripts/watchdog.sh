#!/bin/bash
cd $(dirname $0)
java -classpath config/:lib/* dk.statsbiblioteket.vhsingestmonitor.Watchdog