FROM openjdk:11

LABEL Mainteiner="Kovtun Evgeniya, eekovtun@gmail.com"

ARG GEOMAGNETIC_HOME=/opt/geomagnetic
ARG GEOMAGNETIC_JAR=geomagnetic.jar

ENV TZ=Europe/Moscow \
    APP_HOME=$GEOMAGNETIC_HOME \
    APP_JAR=$GEOMAGNETIC_JAR

WORKDIR $APP_HOME

COPY build/libs/geomagnetic.jar $APP_HOME/$APP_JAR

ENTRYPOINT java $GEOMAGNETIC_OPTS -jar $APP_JAR