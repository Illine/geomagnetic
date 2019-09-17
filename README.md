# Weather
The services is designed for an android application [Weather: Any place on the Earth!](https://play.google.com/store/apps/details?id=net.c7j.wna&hl=ru "Google Play")

### Functional services


##### Geomagnetic API

The service requests a geomagnetic forecast from [SWPC NOAA API](https://services.swpc.noaa.gov/text/3-day-geomag-forecast.txt "Geomagnetic Forecast as .txt file") as a common .txt file on a daily basis. After the file with forecast is received and parsed by the service, the result is stored to the database. All the process is entirely self-acting.  
The Weather Application accesses the API via REST.


### Infrastructure services

##### Config Service
The service is used for a loading of properties from a private git repository via ssh. The Config Service is launched by default port: 8888 and has a root path: /config-service

### Tech
The Weather uses next libraries:

* [Spring Boot]
* [Spring Web]
* [Spring JPA]
* [Spring Actuator]
* [Spring Validation]
* [Spring Cloud Config]
* [Spring Cloud Config Server]
* [Hibernate]
* [Model Mapper]
* [Liquibase]
* [PostgreSQL]
* [Lombok]
* [JUnit5]
* [H2]
* [P6 SPY]

## How to run this project?

**Note that** before the first start-up of the application you will need the Internet because a Gradle Wrapper will be downloaded, also you should have an installed Docker and Docker Compose!

### Build
The application can be built via one of the next gradle command:
 
`./gradlew build` on a Linux machine  
`gradlew.bat build` on a Windows machine

### Docker

**Before you start, make sure a build passes without errors!**

#### Development mode
If you want to launch this project yourself (as a developer, for example) you should have a cloned project and assembled artifact,
after that run a command:  
`source weather_env.sh && docker-compose -f docker-compose.yaml -f docker-compose-dev.yaml up -d`  
in a root directory of the one.   

Images will be created and run:
* _postgres:9.5-alpine as config-service, a port 5432_
* _weather/config-service:dev as config-service, a port 8888_
* _weather/geomagnetic-api:dev as geomagnetic-api, a port 8001_

For a comfortable developing and testing all services are launched via a 'bridge' network mode.

When you will need to stop a running application, input a command:  
`source weather_env.sh && docker-compose -f docker-compose.yaml -f docker-compose-dev.yaml -v --rmi all`  
in a root of the project.  

After completion of the command, the images will be removed (include a postgres!), also created networks and volumes will be deleted.

#### Testing mode

#### Production mode

#### License
[MIT](LICENSE)

This project itself is open source with a [public repository][git-repo].


[Spring Boot]: <https://spring.io/projects/spring-boot>
[Spring Web]: <https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web/2.1.5.RELEASE>
[Spring JPA]: <https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa/2.1.5.RELEASE>
[Spring Actuator]: <https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-actuator/2.1.5.RELEASE>
[Spring Validation]: <https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation/2.1.5.RELEASE>
[Spring Cloud Config]: <https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-config/2.1.3.RELEASE>
[Spring Cloud Config Server]: <https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-config-server/2.1.1.RELEASE>
[Hibernate]: <http://hibernate.org/>
[Model Mapper]: <http://modelmapper.org/>
[Liquibase]: <https://www.liquibase.org/>
[PostgreSQL]: <https://www.postgresql.org/>
[Lombok]: <https://projectlombok.org/>
[JUnit5]: <https://junit.org/junit5/>
[H2]: <https://www.h2database.com/html/main.html>
[P6 SPY]: <https://p6spy.readthedocs.io/en/latest/>
[git-repo]: <https://github.com/Illine/geomagnetic-api>
