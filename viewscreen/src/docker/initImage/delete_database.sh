mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} -e "DROP DATABASE IF EXISTS ${APP_DATABASE};"

# 单机环境下删除映射到主机上的目录
VIP='vip.cluster.local'
if ssh ${VIP} test -d /vms/viewscreen; then
    ssh ${VIP} rm -rf /vms/viewscreen
fi