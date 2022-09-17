# Principles of cloud native
- one application one codebase
- dependecy management = use a toll to manage dependecies explicity, don't rely on dependencies from the env
- In case of code needed by more than one application, you should either turn it into an independent service itself or into a library that you can import into the project as a dependency
- ./gradlew bootJar = creates an executable jar
## Embedded Tomcat
- The DispatcherServlet provides a central entry point for request processing
- You can configure an embedded web server in two ways: through properties or in a WebServerFactoryCustomizer bean
- server.tomcat.connection-timeout = property defines a limit for how much time Tomcat should wait between accepting a TCP connection from a client and actually receiving the HTTP request.
    - default is 20s but the recommended is 2-5s
- server.tomcat.keep-alive-timeout = property to configure how long to keep a connection open while waiting for new HTTP requests.
- server.tomcat.threads.max = maximum number of request processing threads
- server.tomcat.threads.min-spare = minimum number of threads that should always be kept running\
example config :
server:
  port: 9001
  tomcat:
    connection-timeout: 2s
    keep-alive-timeout: 15s
    threads:
      max: 50
      min-spare: 5

#  Building a RESTful application with Spring MVC
- The 15-Factor methodology promotes the API first pattern. It encourages you to establish the service interface first and work on the implementation later
- Spring MVC relies on an HttpMessageConverter bean to convert the returned object into a specific representation the client supports. The decision about the content type is driven by a process called content negotiation, during which the client and the server agree on a representation that both can understand.
- The client can inform the server about which content types it supports through the Accept header in the HTTP request

# Testing
- WebTestClient
  - You can now use the WebTestClient class to test REST APIs both on mock environments and running servers. Compared to MockMvc and TestRestTemplate, WebTestClient provides a modern and fluent API and additional features

- Spring Boot offers the possibility of using contexts initialized only with a subgroup of components (beans), targeting a specific application slice. Slice tests don’t use the @SpringBootTest annotation, but one among a set of annotations dedicated to particular parts of an application: Web MVC, Web Flux, REST Client, JDBC, JPA, Mongo, Redis, JSON, and others

- Mocks created with the @MockBean annotation are different from a standard mock (for example, one created with Mockito) since the class is not only mocked, but the mock is also included in the application context. So, whenever the context is asked to autowire that bean, it automatically injects the mock rather than the actual implementation.

- Using the @JsonTest annotation, you can test JSON serialization and deserialization for your domain objects. @JsonTest loads a Spring application context and auto-configures the JSON mappers for the specific library in use (by default, it’s Jackson). Furthermore, it configures the JacksonTester utility you can use to check that the JSON mapping works as expected, relying on the JsonPath and JSONAssert libraries.

# Deployment pipeline: Build and test
- Commit stage. After a developer commits new code to the mainline, this stage goes through build, unit tests, integration tests, static code analysis, and packaging. At the end of this stage, an executable application artifact is published to an artifact repository. That is a release candidate. For example, it can be a JAR artifact published to a Maven repository or a container image published to a container registry. This stage supports the continuous integration practice. It’s supposed to be fast, possibly under five minutes, to provide developers with fast feedback about their changes and allow them to move on to the next task.

- Acceptance stage. The publication of a new release candidate to the artifact repository triggers this stage, which consists of deploying the application to production-like environments and running additional tests to increase the confidence about its releasability. The tests running in the acceptance stage are usually slow, but we should strive to keep the whole deployment pipeline execution under one hour. Examples of tests included in this stage are functional acceptance tests and non-functional acceptance tests, such as performance tests, security tests, and compliance tests. If necessary, this stage can also include manual tasks like exploratory and usability tests. At the end of this stage, the release candidate is ready to be deployed to production at any time. If we are still not confident about it, this stage is missing some tests.

- Production stage. After a release candidate has gone through the commit and acceptance stages, we are confident enough to deploy it to production. This stage is triggered manually or automatically, depending on whether it’s been decided to adopt a continuous deployment practice. The new release candidate is deployed to a production environment using the same deployment scripts employed (and tested) in the acceptance stage. Optionally, some final automated tests can be run to verify the deployment was successful.

- Each workflow is organized into jobs that run in parallel. 