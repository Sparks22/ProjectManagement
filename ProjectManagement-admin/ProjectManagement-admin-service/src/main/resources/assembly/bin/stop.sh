BIN_PATH=$(cd `dirname $0`; pwd)

cd `dirname $0`

cd ..

BASE_PATH=`pwd`

files=$(ls $BASE_PATH)

for filename in $files
do
  if [[ $filename =~ \.jar$ ]]; then
      APPLICATION_JAR=$filename
  fi
done

if [[ -z $APPLICATION_JAR ]]; then
    echo "APPLICATION_JAR is empty,Please check the package structure!!!"
    exit
fi
PIDS=`ps -ef | grep $APPLICATION_JAR | grep -v grep | awk '{print $2}' `

if [[ -z "${PIDS}" ]]; then
    echo "${APPLICATION_JAR} is already stopped"
else
    echo -e "Stopping the ${APPLICATION_JAR} ... \c"
    for PID in ${PIDS}; do
        kill ${PID} > /dev/null 2>&1
    done

    COUNT=0
    while [ ${COUNT} -lt 1 ]; do
        echo -e ".\c"
        sleep 1
        COUNT=1
        for PID in ${PIDS}; do
            PID_EXIST=`ps -f -p ${PID} | grep java`
            if [ -n "${PID_EXIST}" ]; then
                COUNT=0
                break
            fi
        done
    done

    echo "OK!"
    echo "<<<<<<<${APPLICATION_JAR}>>>>>>> stopped successfully! PID: ${PID}"
fi