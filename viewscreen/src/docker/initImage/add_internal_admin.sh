#!/bin/bash

set -x

currentdir=$(dirname "$0")
echo ${currentdir}

CONN_KEYSTONE="mysql -u${MYSQL_USER} -p${MYSQL_PWD} -h${MYSQL_SVC_HOST} -P${MYSQL_SVC_PORT} keystone"

VDI_CONTROLLER_ADMIN_NAME="vdiControllerAdmin"

function insertUser
{
    insertResult=`${CONN_KEYSTONE} < ${currentdir}/add_internal_admin.sql 2>&1`
    count=0
    while ((count<30))
    do
        if [ -n "$insertResult" ];then
            echo 'result:'$insertResult
            echo 'wait 5 seconds...'
            sleep 5
            insertResult=`${CONN_KEYSTONE} < ${currentdir}/add_internal_admin.sql 2>&1`
        else
            echo 'result:'$insertResult
            echo 'break'
            break
        fi
        let "count+=1"
        echo "retry ${count} times"
    done
}

function findUser
{
    vdiControllerLocalUserId=`${CONN_KEYSTONE} -N -e "select id from keystone.local_user where name='${VDI_CONTROLLER_ADMIN_NAME}'"`
    echo "vdi controller localUserId is ${1}"
    if [ -n "${vdiControllerLocalUserId}" ]; then
        echo "vdi controller user has already insert"
    else
        echo "init vdi controller user"
        insertUser
    fi
}

main()
{
  findUser
}
main
