#!/bin/bash

# 程序目录
APP_HOME='/data/'
# 程序名称
APP_NAME='serverApplication.jar'
# gc日志目录
GC_LOG="${APP_HOME}logs/job_execute_gc.log"
# 程序启动参数，禁止修改
JAVA_OPTIONS=" -Xmx32g -Xms32g -XX:+UnlockExperimentalVMOptions -Dio.netty.maxDirectMemory=1073741824 -Xlog:gc:$GC_LOG -XX:+PrintGCDetails -XX:+ParallelRefProcEnabled -XX:G1HeapWastePercent=5 -XX:G1NewSizePercent=25 -XX:G1MaxNewSizePercent=60 -XX:InitiatingHeapOccupancyPercent=40 -XX:G1MixedGCLiveThresholdPercent=90 -XX:MaxDirectMemorySize=1g  -XX:+UseG1GC -XX:ParallelGCThreads=10 -XX:ConcGCThreads=4 -XX:G1HeapRegionSize=16m -XX:MaxGCPauseMillis=200 -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=512M -XX:+HeapDumpOnOutOfMemoryError"
# 关闭程序等待时间（单位：秒）
SHUTDOWN_WAIT_TIME=30

# 使用说明, 用来提示输入参数
usage() {
	echo "Usage: sh smsc.sh [start|stop|restart|status|version]"
	exit 1
}

# 检查程序是否运行中
is_exist() {
	pid=` ps -ef | grep java | grep $APP_NAME | grep -v grep | awk '{print $2}' `
	if [ -z "${pid}" ]; then
		return 1
	else
		return 0
	fi
}

# 检查版本
version() {
	java $JAVA_OPTIONS -jar $APP_HOME$APP_NAME -v
	exit 1
}

# 启动程序
start() {
	is_exist

	if [ $? -eq "0" ]; then
		echo -e "\n>>> ${APP_NAME} is already running. PID=${pid}<<<"

	else
		nohup java $JAVA_OPTIONS -jar $APP_HOME$APP_NAME > /dev/null 2>&1 &

		is_exist

		if [ $? -eq "0" ]; then
			echo -e "\n>>> ${APP_NAME} started. PID=${pid}<<<"
		fi
	fi
}

# 停止程序
stop() {
	is_exist

	if [ $? -eq "0" ]; then
		kill $pid

		let kwait=$SHUTDOWN_WAIT_TIME
		count=0
		until [ `ps -p $pid | grep -c $pid` = '0' ] || [ $count -gt $kwait ]
		do
			echo -n -e "\n\e[00;31mwaiting for processes to exit\e[00m"
			sleep 1
			let count=$count+1
		done

		if [ $count -gt $kwait ]; then
			echo -n -e "\n\e[00;31mkilling processes didn't stop after $SHUTDOWN_WAIT_TIME seconds\e[00m"
			kill -9 $pid
		fi

		echo -e "\n${APP_NAME} stoped."
	else
		echo -e "\n${APP_NAME} is not running"
	fi
}

# 查看状态
status() {
	is_exist

	if [ $? -eq "0" ]; then
		echo -e "\n${APP_NAME} is running. PID=${pid}"
	else
		echo -e "\n${APP_NAME} is not running."
	fi
}

# 重启
restart() {
	stop

	sleep 3

	start
}

# 选项
case "$1" in
	"start")
		start
		;;
	"version")
		version
		;;
	"stop")
		stop
		;;
	"status")
		status
		;;
	"restart")
		restart
		;;
	*)
		usage
		;;
esac