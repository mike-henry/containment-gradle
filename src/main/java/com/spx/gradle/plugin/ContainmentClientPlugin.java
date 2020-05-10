package com.spx.gradle.plugin;

import org.gradle.api.Project;
import org.springframework.boot.gradle.plugin.SpringBootPlugin;

public class ContainmentClientPlugin extends  GeneralPlugin {

  private static final String IO_SPRING_DEPENDENCY_MANAGEMENT = "io.spring.dependency-management";
  private final String apis[] = {
      "org.apache.commons:commons-lang3:3.9",
      "org.slf4j:slf4j-api:1.7.30",
      "org.springframework.boot:spring-boot-starter-jersey",
      "javax.ws.rs:javax.ws.rs-api:2.0.1",
      "io.swagger:swagger-jersey2-jaxrs:1.5.8",
      "io.springfox:springfox-swagger2:2.9.2",
      "io.springfox:springfox-swagger-ui:2.9.2",
      "jakarta.validation:jakarta.validation-api:2.0.2",
      "javax.annotation:javax.annotation-api:1.3.2"
  };

  private final String implementations[] ={
      "org.springframework.boot:spring-boot-configuration-processor",
       "org.springframework.boot:spring-boot-starter-actuator",
       "org.glassfish.jersey.security:oauth2-client:2.27",
       "org.springframework.boot:spring-boot-starter-oauth2-client",
       "org.springframework.boot:spring-boot-starter-security",
       "org.springframework.boot:spring-boot-starter-web",
       "org.springframework.boot:spring-boot-autoconfigure",
       "org.reflections:reflections:0.9.12",
       "org.springframework.boot:spring-boot-starter-websocket"
   };

  private final String compileOnlys[] = {
       "javax.websocket:javax.websocket-api:1.1"
  };

  private final DependencyInjector dependencies = new DependencyInjector();

  @Override
  public void apply(Project project) {
    super.apply(project);
    project.getPlugins().apply(SpringBootPlugin.class);
    project.getPlugins().apply(IO_SPRING_DEPENDENCY_MANAGEMENT);
    project.afterEvaluate(p ->{
      dependencies.addImplementation(p, implementations);
      dependencies.addApi(p, apis);
      dependencies.addCompileOnly(p, compileOnlys);
    });
  }
}
