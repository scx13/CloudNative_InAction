- For the Catalog Service application, we’ll use a relational database, PostgreSQL, to store the data about the books in the catalog

## docker command to spin up db
$ docker run -d --name polar-postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=polardb_catalog -p 5432:5432 postgres:14.3
- the project will use the following dependencies:
    Spring Data JDBC (org.springframework.boot:spring-boot-starter-data-jdbc) provides the necessary libraries to persist data in relational databases using Spring Data and JDBC.
    PostgreSQL (org.postgresql:postgresql) provides a JDBC driver that allows the application to connect to a PostgreSQL database.

- Opening and closing database connections are relatively expensive operations, so you don’t want to do that every time your application accesses data. The solution is connection pooling: the application establishes several connections with the database and reuses them, rather than creating new ones at each data access operation. That is a considerable performance optimization.We will use hikari comes default with spring boot

- . You want to configure at least a connection timeout (spring.datasource.hikari.connection-timeout) and a maximum number of connections in the pool (spring.datasource.hikari.maximum-pool-size), because they both affect application resilience and performance.

- Spring Data JDBC encourages working with immutable entities. Using Java records to model entities is an excellent choice since they’re immutable by design and expose an all-args constructor the framework can use to populate objects.

- Spring Data JDBC supports optimistic locking.

- When the @Id field is null and the @Version field is 0, Spring Data JDBC assumes it’s a new object. Consequently, it relies on the database to generate an identifier when inserting the new row in the table.

- Spring Data JPA works with mutating objects, so you can’t use Java records. JPA entity classes must be marked with the @Entity annotation and expose a no-args constructor. JPA identifiers are annotated with @Id and @Version from the javax.persistence package instead of org.springframework.data.annotation.

- Spring Data offers a feature to initialize a data source at startup time. By default, you can use a schema.sql file to create schemas and a data.sql file to insert data in the newly created tables. Such files should be placed in the src/main/resources folder.

- By default, Spring Data loads the schema.sql file only when using an embedded, in-memory database. Since we’re using PostgreSQL, we need to enable the functionality explicitly. In the application.yml file for your Catalog Service project, add the following configuration to initialize the database schema from the schema.sql file.

- When persisting data, it’s useful to know the creation date for each row in a table and the date of when it’s been updated last. After securing an application with authentication and authorization, you can even register who created each entity and recently updated it. All of that is called database auditing.

-In Spring Data JPA, you would use the @EnableJpaAuditing annotation to enable JPA auditing, and you would annotate the entity class with @EntityListeners(AuditingEntityListener.class) to make it listen to audit events, which doesn’t happen automatically like in Spring Data JDBC.

- The repositories provided by Spring Data come configured with transactional contexts for all the operations. For example, all methods in CrudRepository are transactional. That means you can safely call the saveAll() method, knowing that it will be executed in a transaction.

- When you add your own query methods as you did for BookRepository, it’s up to you to define which ones should be part of a transaction. You can rely on the declarative transaction management provided by the Spring Framework and use the @Transactional annotation (from the org.springframework.transaction.annotation package) on classes or methods to ensure they are executed as part of a single unit of work.

## Testing data persistence with Spring and Testcontainers

- First, you need to add a dependency to the Testcontainers module for PostgreSQL in the build.gradle file of your Catalog Service project. Remember to refresh/reimport the Gradle dependencies after the new addition.

- When running tests, we want the application to use a PostgreSQL instance provided by Testcontainers rather than the one we configured earlier via the spring.datasource.url property.

- When the integration profile is enabled, Spring Boot will use the PostgreSQL container instantiated by Testcontainers. We’re now ready to write autotests to verify the data persistence layer.

- Create a BookRepositoryJdbcTests class and mark it with the @DataJdbcTest annotation. That will trigger Spring Boot to include all Spring Data JDBC entities and repositories in the application context. It will also autoconfigure JdbcAggregateTemplate, a lower-level object we can use to set up the context for each test case instead of using the repository (the object under testing).

- The @DataJdbcTest annotation encapsulates handy features. For example, it makes each test method run in a transaction and rolls it back at its end, keeping the database clean. After running the test method in listing 5.18, the database will not contain the book created in findBookByIsbnWhenExisting() because the transaction is rolled back at the end of the method execution.