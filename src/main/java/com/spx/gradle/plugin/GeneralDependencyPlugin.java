package com.spx.gradle.plugin;

import java.util.stream.Stream;
import javax.inject.Inject;
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


  protected final DependencyInjector dependencyInjector;

  String[] plugins = {
      "io.spring.dependency-management"
  };


  public GeneralDependencyPlugin() {
    this.dependencyInjector = new DependencyInjector();
  }


  @Override
  public void apply(Project project) {
    project.getPlugins().apply(JavaPlugin.class);
    project.afterEvaluate(p -> {
      p.getRepositories().mavenCentral();
      p.getRepositories().mavenLocal();
      dependencyInjector.addDefaults(p);
    });

  }
}
