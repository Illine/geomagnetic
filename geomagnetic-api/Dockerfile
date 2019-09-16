FROM openjdk:11

LABEL Mainteiner="Kovtun Evgeniya, eekovtun@gmail.com" \
      Version="1.0.0"

ARG GEOMAGNETIC_HOME=/opt/geomagnetic-api
ARG GEOMAGNETIC_JAR=geomagnetic.jar

ENV APP_HOME=$GEOMAGNETIC_HOME \
    APP_JAR=$GEOMAGNETIC_JAR

WORKDIR $APP_HOME

COPY build/libs/geomagnetic-api-*.jar $APP_HOME/$APP_JAR

ENTRYPOINT java $GEOMAGENTIC_OPTS -jar $APP_JAR