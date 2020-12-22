#!/bin/bash 
mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} -e "DROP DATABASE IF EXISTS viewscreen;"
mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} -e "CREATE DATABASE viewscreen DEFAULT CHARACTER SET utf8;"
mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} --default-character-set=utf8 -e "source /opt/scripts/viewscreen.sql;"
mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} viewscreen -e "INSERT INTO TBL_PARAMETER(TYPE, NAME, VALUE) VALUES ('sys_conf', 'cloud.version', '${VERSION}');"
