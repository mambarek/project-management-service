### Project Management Service

Projects are consumed from Employees Servive 

### H2
http://localhost:8020/h2-console

### Swagger UI
ATTENTION: HAL-Browser and Explorer don't work with Swagger
it rises exceptions so avoid it to add them
#### api-docs
http://localhost:8020/v3/api-docs
#### YAML
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
