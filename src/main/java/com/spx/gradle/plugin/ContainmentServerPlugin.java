package com.spx.gradle.plugin;


import org.gradle.api.Project;

public class ContainmentServerPlugin extends  GeneralPlugin {

  private final String apis[] = {
      "org.apache.commons:commons-lang3:3.9",
      "org.slf4j:slf4j-api:1.7.30",
      "org.springframework.boot:spring-boot-starter-jersey:2.2.4.RELEASE",
      "javax.ws.rs:javax.ws.rs-api:2.0.1",
      "io.swagger:swagger-jersey2-jaxrs:1.5.8",
      "io.springfox:springfox-swagger2:2.9.2",
      "io.springfox:springfox-swagger-ui:2.9.2",
      "org.springframework.boot:spring-boot-starter-data-neo4j:2.2.4.RELEASE",
      "jakarta.validation:jakarta.validation-api:2.0.2",
      "javax.annotation:javax.annotation-api:1.3.2"
  };

  private final String implementations[] ={
       "org.springframework.boot:spring-boot-configuration-processor:2.2.4.RELEASE",
       "org.springframework.boot:spring-boot-starter-aop:2.2.4.RELEASE",
       "org.springframework.boot:spring-boot-starter-actuator:2.2.4.RELEASE",
       "org.glassfish.jersey.security:oauth2-client:2.27",
       "org.springframework.boot:spring-boot-starter-oauth2-client:2.2.4.RELEASE",
       "org.springframework.boot:spring-boot-starter-security:2.2.4.RELEASE",
       "org.springframework.boot:spring-boot-starter-web:2.2.4.RELEASE",
       "org.springframework.boot:spring-boot-autoconfigure:2.2.4.RELEASE",
       "org.reflections:reflections:0.9.12",
       "org.springframework.boot:spring-boot-starter-websocket:2.2.4.RELEASE"
   };

  private final String compileOnlys[] = {
       "javax.websocket:javax.websocket-api:1.1"
  };

  private final DependencyInjector dependencies = new DependencyInjector();

  @Override
  public void apply(Project project) {
    super.apply(project);
    project.afterEvaluate(p ->{
      dependencies.addImplementation(p, implementations);
      dependencies.addApi(p, apis);
      dependencies.addCompileOnly(p, compileOnlys);
    });
  }
}
