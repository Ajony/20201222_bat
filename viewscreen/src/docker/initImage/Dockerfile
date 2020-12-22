FROM blade.h3c.com:443/helm/baseimg:1.5
COPY init_database.sh \
     delete_database.sh \
     viewscreen.sql \
     upgrade_db.sh \
     add_internal_admin.sh \
     add_internal_admin.sql \
     /opt/scripts/
COPY upgrade /opt/scripts/upgrade/
ADD *.json /opt/register_body/