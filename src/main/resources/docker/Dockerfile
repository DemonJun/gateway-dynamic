#指定从哪个镜像开始
FROM openjdk:11-jre-slim
#声明作者
LABEL maintainer=fanjunxiang<fanjx@jinyundt.com>
#设置镜像挂载的数据卷，项目中所有文件路径可以设置在此数据卷中，如日志保存位置等
VOLUME /opt/data
#设置镜像时间区域，避免出现时区不一致现象
RUN echo "Asia/Shanghai" > /etc/timezone && \
    mkdir -p /opt/data && \
    mkdir -p /opt/bin
ENV ADDITIONAL_EUREKA_SERVER_LIST test
ENV ACM_ENDPOINT test
ENV ACM_NAMESPACE test
ENV ACM_GROUP test
#暴露端口
EXPOSE 8890
#拷贝jar文件至镜像中
COPY app.jar  /opt/bin/app.jar
#设置执行命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/bin/app.jar"]