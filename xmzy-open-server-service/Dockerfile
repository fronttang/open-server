#添加JAVA启动的必要镜像
FROM registry.cn-shenzhen.aliyuncs.com/xmzyplat01/openjdk-8-jdk-alpine

#创建一个目录存放jar包
RUN mkdir -p /xmzy

#添加jar包，存放路径以及重命名
ADD ./target/xmzy-open-server-*.jar  /xmzy/xmzy-open-server.jar

#添加进入docker容器后的目录
WORKDIR /xmzy

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

#启动容器执行命令。 -Djava.security.egd=file:/dev/./urandom 可以缩短tomcat启动时间
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/xmzy/xmzy-open-server.jar"]
