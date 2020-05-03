package com.spx.gradle.plugin;

import java.util.stream.Stream;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralDependencyPlugin implements Plugin<Project> {

  private static Logger log  = LoggerFactory.getLogger(GeneralPlugin.class);

  private static final String IMPLEMENTATION = "implementation";
  private static final String ANNOTATION_PROCESSOR = "annotationProcessor";
  private static final String TEST_IMPLEMENTATION = "testImplementation";

  private static final String[] implementations = {
      "org.glassfish.jersey.ext:jersey-proxy-client:2.16",
      "org.glassfish.jersey.core:jersey-client:2.16",
      "org.glassfish.jersey.core:jersey-server:2.16",
      "org.projectlombok:lombok:1.18.12",
      "org.projectlombok:lombok:1.18.12",
      "ch.qos.logback:logback-classic:1.2.3",
      "org.apache.commons:commons-lang3:3.9"

  };

  private static final String[] testImplementations ={
      "org.junit.jupiter:junit-jupiter-api:5.6.2"
  };

  private static final String[] annotationProcessors = {
      "org.projectlombok:lombok:1.18.12"
  };


  String[] plugins = {
      "io.spring.dependency-management"
  };


  @Override
  public void apply(Project project) {
    project.getPlugins().apply(JavaPlugin.class);
    project.afterEvaluate(p -> {
      p.getRepositories().mavenCentral();
      p.getRepositories().mavenLocal();
      addDependencies(p, IMPLEMENTATION, implementations);
      addDependencies(p, ANNOTATION_PROCESSOR,annotationProcessors);
      addDependencies(p, TEST_IMPLEMENTATION,testImplementations);
    });

  }

  private void addDependencies(Project project,String type,String[] dependencies){
    Stream.of(dependencies)
        .forEach( dependency->project.getDependencies().add(type,dependency));
  }
}
