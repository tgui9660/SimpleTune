#!/bin/bash
parentdir=${0%/*}
cd $parentdir

java -Xms128m -Xmx512m -Djava.library.path=libs -jar ST.jar
