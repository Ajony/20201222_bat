#!/bin/bash

echo "------------------------------------------------viewscreen-db-upgrade start---------------------------"
VIEWSCREEN_CURRENT_VERSION=`mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} viewscreen <<EOF | tail -n +2
select VALUE from TBL_PARAMETER where TYPE='sys_conf' and NAME='cloud.version';
EOF`
VIEWSCREEN_INNER_VERSION=${VIEWSCREEN_CURRENT_VERSION#*-}
VIEWSCREEN_OUT_VERSION=${VIEWSCREEN_CURRENT_VERSION%-*}
echo "current version: ${VIEWSCREEN_CURRENT_VERSION}, out:${VIEWSCREEN_OUT_VERSION}, inner:${VIEWSCREEN_INNER_VERSION}"

echo "Begin to install viewscreen-server database and data..."

PARA_VIEWSCREEN_VERSION=$VIEWSCREEN_OUT_VERSION

state=1

#dump mysql
mysqldump -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} --databases viewscreen > /tmp/viewscreen/viewscreen.dump_$(date +%Y%m%d)_$(date +%H%M%S).sql
EXIT_CODE=$? ; if [ $EXIT_CODE -gt 0 ] ; then  echo "upgrade database error!" ; fi

while [ $state -eq 1 ]
do
    if [ "${PARA_VIEWSCREEN_VERSION}" -eq "${VERSION%-*}" ]; then
        break
    fi
    case $PARA_VIEWSCREEN_VERSION in
	 "E5101")

            echo "Begin to exec E5101-E5102 script..."

            mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} viewscreen -e "source /opt/scripts/upgrade/E5101-E5102/upgrade_script.sql"
            EXIT_CODE=$? ; if [ $EXIT_CODE -gt 0 ] ; then  exit $EXIT_CODE ; fi

            echo "End to exec E5101-E5102 script..."

            echo "Begin to exec E5101-E5102 update data script..."

            mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} viewscreen -e "source /opt/scripts/upgrade/E5101-E5102/update_table_data.sql"
            EXIT_CODE=$? ; if [ $EXIT_CODE -gt 0 ] ; then  exit $EXIT_CODE ; fi

            echo "End to exec update E5101-E5102 update data script..."

            PARA_VIEWSCREEN_VERSION="E5102"
            ;;
     "E5102")

            echo "Begin to exec E5102-E5103 script..."

            mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} viewscreen -e "source /opt/scripts/upgrade/E5102-E5103/upgrade_script.sql"
            EXIT_CODE=$? ; if [ $EXIT_CODE -gt 0 ] ; then  exit $EXIT_CODE ; fi

            echo "End to exec E5102-E5103 script..."

            echo "Begin to exec E5102-E5103 update data script..."

            mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} viewscreen -e "source /opt/scripts/upgrade/E5102-E5103/update_table_data.sql"
            EXIT_CODE=$? ; if [ $EXIT_CODE -gt 0 ] ; then  exit $EXIT_CODE ; fi

            echo "End to exec update E5102-E5103 update data script..."

            PARA_VIEWSCREEN_VERSION="E5103"
            ;;
     "E5103")
            PARA_VIEWSCREEN_VERSION="E5103P01"
            ;;
     "E5103P01")
            echo "Begin to exec E5103P01-E5103P02 script"

            mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} viewscreen -e "source /opt/scripts/upgrade/E5103P01-E5103P02/upgrade_script.sql"
            EXIT_CODE=$? ; if [ $EXIT_CODE -gt 0 ] ; then  exit $EXIT_CODE ; fi

            echo "End to exec E5103P01-E5103P02 script..."

            echo "Begin to exec E5103P01-E5103P02 update data script..."

            mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} viewscreen -e "source /opt/scripts/upgrade/E5103P01-E5103P02/update_table_data.sql"
            EXIT_CODE=$? ; if [ $EXIT_CODE -gt 0 ] ; then  exit $EXIT_CODE ; fi

            echo "End to exec update E5103P01-E5103P02 update data script..."

            PARA_VIEWSCREEN_VERSION="E5103P02"
            ;;
     *)

            state=0
            ;;
  esac
done

mysql -h${MYSQL_SVC_HOST} -u${MYSQL_USER} -p${MYSQL_PWD} viewscreen -e "update TBL_PARAMETER set VALUE='${VERSION}' where TYPE='sys_conf' and NAME='cloud.version'"

echo  "End to exec clean bt and upgrade config script..."
echo  "The update of viewscreen-server database finished successfully, thanks."

echo "------------------------------------------------viewscreen-db-upgrade end---------------------------"
