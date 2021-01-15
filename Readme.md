### Project Management Service

Projects are consumed from Employees Servive 

### H2
http://localhost:8020/h2-console

### OpenAPI
We use the Springdoc-openapi
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>1.4.3</version>
        </dependency>
See for details        
https://springdoc.org/        
        
### Swagger UI
ATTENTION: HAL-Browser and Explorer don't work with Swagger
it rises exceptions so avoid it to add them
  
#### api-docs json format
http://localhost:8020/v3/api-docs

#### YAML format to download
http://localhost:8020/v3/api-docs.yaml

#### swagger ui
http://localhost:8020/swagger-ui.html


### Postgres DB

#### Make sure your Postgres is running
>spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect  
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres  
spring.datasource.username=postgres 
spring.datasource.password=postgres  
\#drop n create table again, good for testing, comment this in production  
spring.jpa.hibernate.ddl-auto=none  
spring.jpa.show-sql=true  
\# to activate generation from schema.sql  
spring.datasource.initialization-mode=always
>
In <b>production</b>, we create the database with sql scripts under resources/db manually.
For integration tests we can use the auto-generation mechanism or the schema.sql 

### Split Unit test from Integration tests
in pom.xml add the maven surfire plugin and separate unit tests for IT tests
Unit test run in maven goal test and Integration tests run in maven goal verify  
So you can in jenkins run only unit tests "mvn test" run integration test too with "mvn verify"

## Cors Problem
Cors problems are now fixed with th WebConfig class

## Docker build with fabric8
Docker image would be created and pushed by maven at install phase see pom.xml
> mvn install

or use docker:build, docker:push
> mvn docker:build docker:push

or use Jenkins. In jenkinsfile (Pipeline) we call the two goals

