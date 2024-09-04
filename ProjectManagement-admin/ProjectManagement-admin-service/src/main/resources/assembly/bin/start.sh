#!/bin/bash --login

#usage(){
#    echo "
#Usage:
#  start.sh [-p profile] [-d debug] [-j jmx] [-h] [-c] [-t tenantId] [-z dataCenter] [-w workspace] [-l logicUnitId] [-y phyUnitId] [-r logRootDir] [-m cluster]
#  -p, --profile       spring.profiles.active
#  -d, --debug        active debug, input port
#  -j, --jmx          active jmx, input port
#  -h, --help         display this help and exit
#  -c, --console      the console starts
#  -t, --tenantId     app.galaxy.tenantId
#  -z, --dataCenter   app.galaxy.dataCenter
#  -w, --workspace   app.galaxy.workspace
#  -l, --logicUnitId  app.galaxy.logicUnitId
#  -y, --phyUnitId    app.galaxy.phyUnitId
#  -r, --logRootDir   log root dir
#  -m, --model       standalone or cluster, Set up nacos startup
#"
#}
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
BASE_DIR=`pwd`
echo "-->>BASE_DIR   :${BASE_DIR}"
# 日志存放目录
LOG_ROOT_DIR=''

# 自定义配置文件路径
CONF_DIR=${BASE_DIR}/conf/config
echo "-->>CONF_DIR   :${CONF_DIR}"
BIN_DIR=${BASE_DIR}/bin
echo "-->>BIN_DIR   :${BIN_DIR}"
# 参数定义
CONSOLE_START=false
SPRING_PROFILES=""
JAVA_DEBUG_OPTS=""
JAVA_JMX_OPTS=""
GALAXY_TENANT_ID=""
GALAXY_DATA_CENTER=""
GALAXY_WORKSPACE=""
GALAXY_LOGIC_UNITID=""
GALAXY_PHY_UNITID=""
profile=""
MODEL=""

#function main() {
#    # 选项: 表示该选项需要选选项值
#    while getopts "p:d:j:h:c:t:w:y:r:m:" arg
#    do
#        case ${arg} in
#            p)
#                profile="$OPTARG"
#                if [ ! -f "$CONF_DIR/application-$profile.yml" -a  ! -f "$CONF_DIR/bootstrap-$profile.yml" ]; then
#                    echo "WARNING: Profile $profile does not exist!"
#                fi
#                if [ -n "$profile" ]; then
#                    SPRING_PROFILES="-Dspring.profiles.active=$profile"
#                fi;;
#            d)
#                debugport="$OPTARG"
#                JAVA_DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=$debugport,server=y,suspend=n"
#                echo "Using debug port: $debugport";;
#            j)
#                jmxport="$OPTARG"
#                JAVA_JMX_OPTS="-Dcom.sun.management.jmxremote.port=$jmxport -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
#                echo "Using jmx port: $jmxport";;
#            h)
#                usage
#                exit 0 ;;
#            c)
#                CONSOLE_START=true
#                echo "Start the application from the console.";;
#            t)
#                GALAXY_TENANT_ID="$OPTARG"
#                echo "Using GALAXY_TENANT_ID: $GALAXY_TENANT_ID";;
#            w)
#                GALAXY_DATA_CENTER="$OPTARG"
#                echo "Using GALAXY_DATA_CENTER: $GALAXY_DATA_CENTER";;
#            y)
#                GALAXY_LOGIC_UNITID="$OPTARG"
#                echo "Using GALAXY_LOGIC_UNITID: $GALAXY_LOGIC_UNITID";;
#            r)
#                LOG_ROOT_DIR="$OPTARG"
#                echo "Using LOG_ROOT_DIR: $LOG_ROOT_DIR";;
#            m)
#                MODEL="$OPTARG"
#                echo "Using model: $MODEL";;
#            ?)
#                usage
#                exit 1 ;;
#        esac
#    done
#}
#SERVER_PORT=`sed -nr '/port: [0-9]+/ s/.*port: +([0-9]+).*/\1/p' config/application.yml`
SERVER_NAME=$(awk -F ':' '{if ($1=="spring.application.name") print $2}' ../conf/config/application.yml | tr -d ' ')
SERVER_PORT=$(awk '/^spring\.server\.port:/ {print $2}' ../conf/config/application.yml | tr -d ' ')
if [ -z "${SERVER_NAME}" ]; then
    SERVER_NAME="ProjectManagement-admin-service"
    echo "----<<SERVER_NAME>>----set---->>${SERVER_NAME}"
fi
if [ -z "${SERVER_PORT}" ]; then
    SERVER_PORT=9600
    echo "----<<SERVER_PORT>>----set---->>${SERVER_PORT}"
fi

echo "Server Name: ${SERVER_NAME}; Server port: ${SERVER_PORT}"

echo "--------------------------------------------------->>BASE_DIR   :${BASE_DIR}"
files=$(ls ${BASE_DIR}/*.jar)
echo "-->>files   :${files}"
for filename in $files
do
  echo "-->>filename   :${filename}"
  if [[ ${filename} =~ \.jar$ ]]; then
      APPLICATION_JAR=${filename}
  fi
done

if [[ -z ${APPLICATION_JAR} ]]; then
    echo "APPLICATION_JAR is empty!!!"
    exit;
fi
pid=`ps -ef|grep ${APPLICATION_JAR}|grep -v grep|awk '{print $2}' `
if [ ! -z "${pid}" ]; then
    echo "-->>${SERVER_NAME}<<--服务正在运行，无法启动！"
    exit 1
fi

if [[ -z "${LOG_ROOT_DIR}" ]]; then
    LOG_ROOT_DIR=/apps/logs
fi

if [ ! -d ${LOG_ROOT_DIR} ]; then
    mkdir -p ${LOG_ROOT_DIR}
fi

TIME_NOW=$(date +'%H%M%S')
DATE_TODAY=$(date +'%Y%m%d')
LOG_FILE_HOU="-${TIME_NOW}.log"
LOG_FILE=${SERVER_NAME}${LOG_FILE_HOU}
LOG_DIR=${LOG_ROOT_DIR}/${SERVER_NAME}/${DATE_TODAY}
if [ ! -d ${LOG_DIR} ]; then
    mkdir -p ${LOG_DIR}
fi
LOG_PATH="${LOG_DIR}/${LOG_FILE}"

vmoption_file_dir=${BIN_DIR}/vmoptions/app.vmoptions
echo "Using vmoptions file path : ${vmoption_file_dir}"
temp_log_dir=`echo ${LOG_DIR}|sed 's/\//%/g'`
temp_date_time=`echo ${TIME_NOW}|sed 's/\//%/g'`
JVM_OPTS=`sed -e "s/_LOG_DIR_/${temp_log_dir}/g; s/_DATE_TIME_/${temp_date_time}/g; s/%/\//g; s/\#.*//g" ${vmoption_file_dir}`
JAVA_OPTS="-Dapp.name=${SERVER_NAME}"
JAVA_OPTS="${JAVA_OPTS} -Dapp.name=${BASE_DIR}"

#执行jar
nohup java ${JVM_OPTS} ${JAVA_OPTS} -jar ${APPLICATION_JAR} > ${LOG_PATH} 2>&1 &

PORT_DETECT_CMD=""
if [ -x /usr/bin/ss ]; then
    PORT_DETECT_CMD="/usr/bin/ss -lnt | grep $SERVER_PORT | wc -l"
elif [ -x /usr/bin/netstat ]; then
    PORT_DETECT_CMD="/usr/bin/netstat -ln | grep $SERVER_PORT | wc -l"
elif [ -x /usr/bin/lsof ]; then
    PORT_DETECT_CMD="/usr/bin/lsof -i :$SERVER_PORT | grep -v COMMAND | wc -l"
fi

COUNT=0
SEPT=0
while true; do
    let "SEPT = SEPT + 1"
    COUNT=`ps -f | grep java | grep ${BASE_DIR} | awk '{print $2}' | wc -l`
    if [ ! $COUNT -gt 0 ]; then
        break
    elif [ -z "${PORT_DETECT_CMD}" ]; then
        break
    fi

    COUNT=`eval ${PORT_DETECT_CMD}`
    [ ${COUNT} -gt 0 ] && break

    if [ $SEPT -ge 50 ]; then
        COUNT=0
        break
    fi

    echo -e ".\c"
    sleep 1
done


if [ ${COUNT} -gt 0 ]; then
    echo "<<<<<<${SERVER_NAME}>>>>>>> start ok!"
    PIDS=`ps -f | grep java | grep ${BASE_DIR} | awk '{print $2}'`
    echo "PID                   : ${PIDS}"
    echo "Using CLASSPATH       : ${CLASSPATH}"
    echo "Using JAVA_HOME       : ${JAVA_HOME}"
    echo "Using JAVA_VERSION    : `${JAVA_HOME}/bin/java -version 2>&1 |awk '{a[NR]=$x} END{for(i=1;i<=NR;i++){printf(a[i]" ")}}'|awk '{print $10,$11,$3,$12}'`"
    echo "Using APPLICATION_JAR : ${APPLICATION_JAR}"
    echo "Using JAVA_OPTS       : ${JAVA_OPTS}"
    echo "Using JVM_OPTS        : ${JVM_OPTS}"
    echo "Using DEPLOY_DIR      : ${BASE_DIR}"
    echo "Using LOG_DIR         : ${LOG_DIR}"
    echo "Using CONF_DIR        : ${CONF_DIR}"
    echo "Nohup LOG_PATH        : ${LOG_PATH}"
else
    echo "!!!!!!!${SERVER_NAME}!!!!!!! start failed!"
    echo "请查看日志输出!"
fi





