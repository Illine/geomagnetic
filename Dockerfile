FROM openjdk:11

LABEL Mainteiner="Kovtun Evgeniya, eekovtun@gmail.com"

ARG APP_HOME=/opt/geomagnetic
ARG APP_JAR=geomagnetic.jar

ENV TZ=Europe/Moscow \
    HOME=$APP_HOME \
    JAR=$APP_JAR

WORKDIR $HOME

COPY build/libs/geomagnetic.jar $HOME/$JAR

ENTRYPOINT java $JAVA_OPTS -jar $JAR