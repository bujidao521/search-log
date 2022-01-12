PRINGBOOT_JAR_NAME='log-search-0.0.1-SNAPSHOT.jar'

PIDS=`ps -ef | grep "$SPRINGBOOT_JAR_NAME" | grep java |awk '{print $2}'`
if [ -z "$PIDS" ]; then
  echo "警告: $SPRINGBOOT_JAR_NAME 未启动!"
fi
echo -e "正在停止 $SPRINGBOOT_JAR_NAME 进程[PIDS] ...\c"
for PID in $PIDS ; do
  kill $PID > /dev/null 2>&1
done
COUNT=0
while [ $COUNT -lt 1 ]; do
  echo -e "......\c"
  sleep 2
  COUNT=1
  for PID in $PIDS ; do
    echo "$PID"
    PID_EXIST=`ps -f -p $PID | grep java`
    echo "$PID_EXIST"
    if [ -n "$PID_EXIST" ]; then
      COUNT=0
      break
    fi
  done
done
echo "$SPRINGBOOT_JAR_NAME 进程停止"
nohup java -jar  /root/search-log-0.0.1-SNAPSHOT.jar  --spring.profiles.active=pro >start.log 2>&1 &

