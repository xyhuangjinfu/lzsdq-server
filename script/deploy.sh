#!/bin/bash

#stop server
pid=`netstat -apn | grep 9999 | awk '{print $7}' | awk -F '/' '{print $1}'`
if [ -z  $pid ] 
then
	echo 'no service'
else
 	kill -9 $pid
fi

#clean and build
cd ..
mvn clean
mvn package

#start server
cd target
nohup java -jar lzsdq-1.0-SNAPSHOT.jar &

