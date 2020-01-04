FROM openjdk:11

LABEL Mainteiner="Kovtun Evgeniya, eekovtun@gmail.com" \
      Version="1.0.0"

ARG CONFIG_SERVICE_HOME=/opt/config-service
ARG CONFIG_SERVICE_JAR=config.jar
ARG CONFIG_SERVICE_OPTS=$CONFIG_SERVICE_OPTS

ENV TZ=Europe/Moscow \
    APP_HOME=$CONFIG_SERVICE_HOME \
    APP_JAR=$CONFIG_SERVICE_JAR

WORKDIR $CONFIG_SERVICE_HOME

COPY build/libs/config-service-*.jar $APP_HOME/$APP_JAR

ENTRYPOINT java $CONFIG_SERVICE_OPTS -jar $APP_JAR