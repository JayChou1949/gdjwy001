FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD target/cloud-paas-service.jar app.jar
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java","-Xmx128m","-Xms64m","-Xss512k","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]