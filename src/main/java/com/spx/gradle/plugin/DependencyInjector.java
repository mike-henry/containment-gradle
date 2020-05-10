package com.spx.gradle.plugin;


import java.util.stream.Stream;
import org.gradle.api.Project;

public class DependencyInjector {

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
      "org.junit.jupiter:junit-jupiter-api:5.6.2",
      "org.mockito:mockito-core:3.1.0"
  };

  private static final String[] annotationProcessors = {
      "org.projectlombok:lombok:1.18.12"
  };

  private enum Type {
    IMPLEMENTATION("implementation"),
    ANNOTATION_PROCESSOR("annotationProcessor"),
    COMPILE_ONLY("compileOnly"),
    TEST_IMPLEMENTATION ( "testImplementation"),
    API("compile");

    private final String configuration;

    Type(String configuration){
      this.configuration =configuration;
    }

    public String getConfiguration(){ return configuration;}
  }

   public void addImplementation(Project project,String[] dependencies){
      this.addDependencies(project,Type.IMPLEMENTATION,dependencies);
   }

   public void addApi(Project project,String[] dependencies){
      this.addDependencies(project,Type.API,dependencies);
   }

   public void addCompileOnly(Project project,String[] dependencies){
      this.addDependencies(project,Type.COMPILE_ONLY,dependencies);
   }

   public void addAnnotationProcessor(Project project,String[] dependencies){
      this.addDependencies(project,Type.ANNOTATION_PROCESSOR,dependencies);
   }

   public void addTestImplementation(Project project,String[] dependencies){
      this.addDependencies(project,Type.TEST_IMPLEMENTATION,dependencies);
   }

  public void addDependencies(Project project,Type type,String[] dependencies){
    Stream.of(dependencies)
        .forEach( dependency->project.getDependencies().add(type.getConfiguration(),dependency));
  }

  public void  addDefaults(Project project){
    this.addImplementation(project,implementations);
    this.addAnnotationProcessor(project,annotationProcessors);
    this.addTestImplementation(project,testImplementations);
  }

}
