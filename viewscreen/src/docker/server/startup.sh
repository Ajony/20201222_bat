#!/bin/bash
# waiting for mysql service init
while true; do
  mysql -u${MYSQL_SVC_USER} -p${MYSQL_SVC_PWD} -h${MYSQL_SVC_HOST} -P${MYSQL_SVC_PORT} -e "select version();" &>/dev/null
  if [ $? -ne 0 ]; then
    echo "waiting for mysql initialization to complete..."
    sleep 10
    continue
  fi
  echo "mysql initialization successful!"
  break
done

VIP=`curl -X GET http://os-param-svc.default.svc.cloudos:2379/v2/keys/matrixConfig/mgVip | jq .node.value | sed 's/"//g'`

while true; do
  if [ -z "${VIP}" ]; then
    VIP=`curl -X GET http://os-param-svc.default.svc.cloudos:2379/v2/keys/matrixConfig/mgVip | jq .node.value | sed 's/"//g'`
    continue
  fi
  break
done



cd /opt/viewscreen
java -jar -Dspring.profiles.active=prod -Dfile.encoding=UTF-8 -Ddruid.mysql.usePingMethod=false -Dspring.datasource.druid.initial-size=5 -Dspring.datasource.druid.min-idle=5 -Dspring.datasource.druid.keep-alive=true -Duser.timezone=GMT+08 -DOS_VIP=${VIP} -Dspring.datasource.username=${MYSQL_SVC_USER} -Dspring.datasource.password=${MYSQL_SVC_PWD} viewscreen-server.jar