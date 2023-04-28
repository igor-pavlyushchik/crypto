# Getting Started
The Postgresql in local docker with default 14.7 image was used.
docker pull postgres:14.7
docker run --name mypostgresql -e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=mypassword -p 5432:5432 -v -d postgres:14.7

Kubernetes was not used, it should be easy, but as I mostly had experience with it as existing infrastructure
on test environment in AWS, it would take some extra time to set up everything and it does not match well
with my Saturday night plans :-). But I've already started working on the Kubernetes studying program
and plan to significantly improve by the end of May my knowledge of this important tool.

For rate limiting, we can use Spring Cloud Gateway, didn't work with it, so implementation would require additional time.


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.6/maven-plugin/reference/html/#build-image)
* [Testcontainers Postgres Module Reference Guide](https://www.testcontainers.org/modules/databases/postgres/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#web)
* [JDBC API](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#data.sql)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#actuator)
* [Testcontainers](https://www.testcontainers.org/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Some queries to play
CREATE TABLE IF NOT EXISTS CRYPTO_ENTRIES (timestamp TIMESTAMPTZ, symbol TEXT, price NUMERIC, PRIMARY KEY (timestamp, symbol));
CREATE TABLE IF NOT EXISTS CRYPTO_EXTREMES (symbol TEXT, time_interval TEXT, old_price NUMERIC, new_price NUMERIC, min_price NUMERIC, max_price NUMERIC, PRIMARY KEY (symbol, time_interval));
SELECT * FROM CRYPTO_ENTRIES;
SELECT * FROM CRYPTO_EXTREMES;
SELECT * FROM CRYPTO_ENTRIES ORDER BY timestamp;

SELECT symbol, time_interval, (max_price - min_price)/min_price as normalized_value FROM CRYPTO_EXTREMES ORDER BY (max_price - min_price)/min_price DESC;
SELECT symbol, time_interval, old_price, new_price, min_price, max_price FROM CRYPTO_EXTREMES where symbol = 'ETH'
SELECT symbol, (MAX(price)- MIN(price))/MIN(price) AS normalized_price FROM CRYPTO_ENTRIES WHERE timestamp::date = '2022-01-01' GROUP BY symbol ORDER BY normalized_price DESC LIMIT 1
