package com.spx.gradle.plugin;

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension;
import java.util.Objects;
import org.gradle.api.Project;
import org.springframework.boot.gradle.plugin.SpringBootPlugin;

public class ContainmentClientPlugin extends  GeneralPlugin {

   private static final String[] apis = {
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

  private static final String[] implementations = {
      "io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE",
      SpringBootPlugin.BOM_COORDINATES,
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

  private final String[] compileOnlys = {
       "javax.websocket:javax.websocket-api:1.1"
  };

  private final DependencyInjector dependencies = new DependencyInjector();

  @Override
  public void apply(Project project) {
    super.apply(project);
    project.getPlugins().apply(DependencyManagementPlugin.class);
    Objects.requireNonNull(project.getExtensions().findByType(DependencyManagementExtension.class))
      .imports( importsHandler ->  importsHandler.mavenBom(SpringBootPlugin.BOM_COORDINATES));
    project.afterEvaluate(plugin ->{
      dependencies.addImplementation(plugin, implementations);
      dependencies.addApi(plugin, apis);
      dependencies.addCompileOnly(plugin, compileOnlys);
    });
  }
}