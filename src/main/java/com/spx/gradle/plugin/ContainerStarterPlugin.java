package com.spx.gradle.plugin;

import java.util.stream.Stream;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerStarterPlugin extends  GeneralPlugin  {

  private static Logger log  = LoggerFactory.getLogger(GeneralPlugin.class);

  private static final String IMPLEMENTATION = "implementation";
  private static final String ANNOTATION_PROCESSOR = "annotationProcessor";
  private static final String TEST_IMPLEMENTATION = "testImplementation";

  private final String[]  implementations = {
      "",
     "org.springframework.boot:spring-boot-starter"
  };

  private final String[]  testImplementations = {
  "org.springframework.boot:spring-boot-starter-test"
  };

  String[] plugins = {
      "io.spring.dependency-management"
  };

  @Override
  public void apply(Project project) {
    project.getPlugins().apply(JavaLibraryPlugin.class);

    project.afterEvaluate(p -> {
      addDependencies(p);
      p.getRepositories().mavenCentral();
      p.getRepositories().mavenLocal();
    });
  }

  private void addDependencies(Project project) {
    Stream.of(implementations)
        .forEach(d ->  project.getDependencies().add(IMPLEMENTATION,d));
    Stream.of(testImplementations)
        .forEach(d ->  project.getDependencies().add(TEST_IMPLEMENTATION,d));
  }
}
