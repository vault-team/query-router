#!/usr/bin/env bash

VAULT_QR_HOME="$(cd "`dirname "$0"`"/..; pwd)"
cd $VAULT_QR_HOME

CONF_HOME=${VAULT_QR_HOME}/conf
LOG_HOME=${VAULT_QR_HOME}/log

LOG_QR_LOG=${LOG_HOME}/qr.log
PID_QR_LOG=${LOG_HOME}/qr.pid

LIB_QR=`ls -d ${VAULT_QR_HOME}/lib`
CLASSPATH_QR=$(JARS=("$LIB_QR"/*.jar); IFS=:; echo "${JARS[*]}")

source $CONF_HOME/vault_query_router.conf

# Function to delete pid in file
kill_process_by_pid_file () {
    pid_file=$1
    target_pid="$(cat ${pid_file})"
    kill "$target_pid"
    rm -f "$pid_file"
}

if [ "$1" == "start" ]
then
    # start api and system monitor and
    if [ -f ${PID_QR_LOG} ] ; then
        echo "please stop vault first"
    else
        echo "starting vault query router....."
        nohup $JAVA_HOME/bin/java \
         -Dhome.dir=$VAULT_QR_HOME \
         -Dworker.id="1" \
         -Dlisten.host=$LISTEN_HOST \
         -Dlisten.port=$LISTEN_PORT \
         -Dbackend.db.host=$BACKEND_DB_HOST \
         -Dbackend.db.port=$BACKEND_DB_PORT \
         -Dbackend.db.schema=$BACKEND_DB_SCHEMA \
         -Dbackend.db.username=$BACKEND_DB_USERNAME \
         -Dbackend.db.password=$BACKEND_DB_PASSWORD \
         -cp $CLASSPATH_QR \
         vault.queryrouter.Main \
         >> "$LOG_QR_LOG" 2>&1 < /dev/null &
        echo $! > "$PID_QR_LOG"
    fi
elif [ "$1" == "stop" ]
then
    echo "stopping vault query router....."
    kill_process_by_pid_file ${PID_QR_LOG}
else
    echo "daemon.sh [start | stop]"
fi