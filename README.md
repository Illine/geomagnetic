# Weather
The services is designed for an android application [Weather: Any place on the Earth!](https://play.google.com/store/apps/details?id=net.c7j.wna&hl=ru "Google Play")

### Functional services


##### Geomagnetic API

The service requests a geomagnetic forecast from [SWPC NOAA API](https://services.swpc.noaa.gov/text/3-day-geomag-forecast.txt) as a common .txt file on a daily basis. After the file with forecast is received and parsed by the service, the result is stored to the database. All the process is entirely self-acting.  
The Weather Application accesses the API via REST.


### Infrastructure services

##### Config Service
The service is used a loading of properties from a private git repository via ssh. The Config Service is launched on a default port: 8888 and has an url: /config-service

### Tech
The Weather uses of next libraries:

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

**Note that** upon the first start-up the application you will need the Internet because a gradle wrapper will be downloaded, also you should have an installed Docker and Docker Compose!

### Build
The application is built via a next gradle command:
 
`gradlew build`

### Docker

**Before you start make sure a build passes without errors!**

#### Development mode
If you'd like to launch this project yourself (as a developer, for example) you have to clone the project and build artifact,
after that run  
`source weather_env.sh && docker-compose -f docker-compose.yaml -f docker-compose-dev.yaml up -d`  
in a root directory of the one.   

Images will be created and run:
* postgres:9.5-alpine as config-service, a port 5432
* weather/config-service:dev as config-service, a port 8888
* weather/geomagnetic-api:dev as geomagnetic-api, a port 8001

For comfortable a developing and testing the all services are launched via a 'bridge' network mode.

When you will need to stop a running application to input a command:  
`source weather_env.sh && docker-compose -f docker-compose.yaml -f docker-compose-dev.yaml -v --rmi all`  
in a root of the of the project.  

After completed the command the all images will be removed (include a postgres!), also will be deleted a created networks and volumes.


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
