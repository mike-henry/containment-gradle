package com.spx.gradle.plugin;

import java.util.Optional;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.component.SoftwareComponent;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralPublishPlugin  implements Plugin<Project> {

  private static Logger log  = LoggerFactory.getLogger(GeneralPublishPlugin.class);

  @Override
  public void apply(Project project) {
    project.getPlugins().apply(MavenPublishPlugin.class);
    project.afterEvaluate(prj -> {
      setupNexus(prj);
      prj.getRepositories().mavenCentral();
      prj.getRepositories().mavenLocal();
    });
  }

  private void setupNexus(Project project) {
    configureNexus(project)
        .map(p -> addComponent(project,p));
  }

  private Optional<PublishingExtension> configureNexus(Project project) {
    String nexusUrl = getProperty(project,"nexusUrl");
    if(nexusUrl.length() == 0) {
      project.getLogger().warn("No nexus url, found will not publish set property'nexusUrl' in gradle.properties");
      return Optional.empty();
    }

    PublishingExtension publishing = project.getExtensions().findByType(PublishingExtension.class);
    if (publishing.getRepositories().isEmpty()){
      log("no repository found  using default:");
      publishing.getRepositories().maven(mavenArtifactRepository -> {
        String relativePathReleases = getProperty(project,"relativePathReleases");
        String relativePathSnapshots = getProperty(project,"relativePathSnapshots");
        String nexusUsername = getProperty(project,"nexusUsername");
        String nexusPassword = getProperty(project,"nexusPassword");
        String nexusUrl1 = getProperty(project,"nexusUrl");
        String version = getVersion(project);
        String  fullPath  =  nexusUrl1 + ( version.endsWith("SNAPSHOT") ? relativePathSnapshots : relativePathReleases);
        mavenArtifactRepository.setUrl(fullPath);
        mavenArtifactRepository.credentials(passwordCredentials -> {
          passwordCredentials.setUsername(nexusUsername);
          passwordCredentials.setPassword(nexusPassword);
        });
      });
    }
    return Optional.of(publishing);
  }

  private PublishingExtension addComponent(Project project,PublishingExtension pe) {
    pe.getPublications().create("mavenJava",MavenPublication.class, publication -> {
      log("publicationType {}" ,publication.getClass().getName());
      SoftwareComponent component = project.getComponents().getByName("java");
      publication.from(component);
    });
    return pe;
  }

  private String getProperty(Project project, String key){
    return Optional.ofNullable(project.getProperties().get(key))
        .map(Object::toString)
        .orElse( "");
  }

  private void log(String message, Object... params) {
    log.info(message,params);
  }

  private String getVersion(Project project) {
    String version = Optional.of(project.getVersion()).map(Object::toString).orElse("0.0.0-SNAPSHOT");
    log("Version: "+version);
    return version;
  }
}