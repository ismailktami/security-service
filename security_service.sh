#!/bin/sh
SERVICE_NAME=service-security
PATH_TO_JAR=demo-0.0.1-SNAPSHOT.jar
PID_PATH_NAME=/tmp/security-service-pid

case $1 in 
	start)
		  echo "Starting $SERVICE_NAME  ....."
		  if [ ! -f $PID_PATH_NAME ]; then
		     nohup java -jar $PATH_TO_JAR /tmp 2 >> /dev/null >> /dev/null & echo $! > $PID_PATH_NAME
		     echo "$PID_PATH_NAME "
		     echo "$SERVICE_NAME started ..."
		  else

		  	echo "$SERVICE_NAME is already runing ..."

		  fi
		 ;;

	stop)
		if [  -f $PID_PATH_NAME ]; then
			PID=$(cat $PID_PATH_NAME);
			kill $PID;
			echo "$SERVICE_NAME stopping ..."
			rm $PID_PATH_NAME
		else 
			echo "$SERVICE_NAME is not running ...."
		fi
		;;
	restart)
	 	if [ ! -f $PID_PATH_NAME ]; then 
	 	   PID=$(cat $PID_PATH_NAME );
	 	   echo "$SERVICE_NAME stopping ....";
	 	   kill $PID;
	 	   echo "$SERVICE_NAME stopped .....";
	 	else
	 	nohup java -jar $PATH_TO_JAR /tmp 2 >> /dev/null >> /dev/null & echo $! >$PID_PATH_NAME
		echo "$SERVICE_NAME started ..."
		fi 
		;;

esac 
