FROM blade.h3c.com:443/h3cloud-tools/openjdk:centos-1.8.0.151-1.b12.el7_4

user root

COPY startup.sh viewscreen-server.jar /opt/viewscreen/
RUN chmod +x /opt/viewscreen/startup.sh && yum install -y mariadb.x86_64 mariadb-libs.x86_64

ENV LC_ALL zh_CN.UTF-8

WORKDIR /opt/viewscreen/

ENTRYPOINT ./startup.sh