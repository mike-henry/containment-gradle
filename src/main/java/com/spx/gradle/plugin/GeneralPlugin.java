package com.spx.gradle.plugin;

import java.util.Optional;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.compile.JavaCompile;

public class GeneralPlugin implements Plugin<Project> {

  public void apply(Project project) {
    project.getPlugins().apply(JavaPlugin.class);
    JavaCompile compileJava = (JavaCompile) project.getTasks().getByName("compileJava");
    compileJava.getOptions().setEncoding("UTF-8");
    project.getPlugins().apply(GeneralDependencyPlugin.class);
    project.getPlugins().apply(GeneralPublishPlugin.class);
    project.afterEvaluate(p -> {
      p.getRepositories().mavenCentral();
      p.getRepositories().mavenLocal();
      p.getRepositories().maven(getInternalMavenRepository(p));
    });
  }

  private Action<? super MavenArtifactRepository> getInternalMavenRepository(Project project) {
    return (Action<MavenArtifactRepository>) mavenArtifactRepository -> {
      mavenArtifactRepository.setName("nexus");
      mavenArtifactRepository.setUrl(getProperty(project,"nexusUrl")+getProperty(project,"relativePath"));
    };
  }

  private String getProperty(Project project, String key){
    return Optional.ofNullable(project.getProperties().get(key))
        .map(Object::toString)
        .orElse( "");
  }
}