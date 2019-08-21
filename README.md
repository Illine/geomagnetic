# Weather
The services is designed for an android application [Weather: Any place on the Earth!](https://play.google.com/store/apps/details?id=net.c7j.wna&hl=ru "Google Play")

### Functional services


##### Geomagnetic API

The service requests a geomagnetic forecast from [SWPC NOAA API](https://services.swpc.noaa.gov/text/3-day-geomag-forecast.txt) as a common .txt file on a daily basis. After the file with forecast is received and parsed by the service, the result is stored to the database. All the process is entirely self-acting.  
The Weather Application accesses the API via REST.


### Infrastructure services


### Tech
The Weather uses of next libraries:

* [Spring Boot]
* [Spring Web]
* [Spring JPA]
* [Spring Actuator]
* [Spring Validation]
* [Spring Cloud Config]
* [Hibernate]
* [Model Mapper]
* [Liquibase]
* [PostgreSQL]
* [Lombok]
* [JUnit5]
* [H2]
* [P6 SPY]

### Build

### Docker

#### License
[MIT](LICENSE)

This project itself is open source with a [public repository][git-repo].


[Spring Boot]: <https://spring.io/projects/spring-boot>
[Spring Web]: <https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web/2.1.5.RELEASE>
[Spring JPA]: <https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa/2.1.5.RELEASE>
[Spring Actuator]: <https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-actuator/2.1.5.RELEASE>
[Spring Validation]: <https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation/2.1.5.RELEASE>
[Spring Cloud Config]: <https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-config/2.1.3.RELEASE>
[Hibernate]: <http://hibernate.org/>
[Model Mapper]: <http://modelmapper.org/>
[Liquibase]: <https://www.liquibase.org/>
[PostgreSQL]: <https://www.postgresql.org/>
[Lombok]: <https://projectlombok.org/>
[JUnit5]: <https://junit.org/junit5/>
[H2]: <https://www.h2database.com/html/main.html>
[P6 SPY]: <https://p6spy.readthedocs.io/en/latest/>
[git-repo]: <https://github.com/Illine/geomagnetic-api>
