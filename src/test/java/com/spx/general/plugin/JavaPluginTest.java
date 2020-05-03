package com.spx.general.plugin;

import com.spx.gradle.plugin.GeneralPlugin;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

public class JavaPluginTest {


  @Test
  public void testGeneral(){
    GeneralPlugin plugin = new GeneralPlugin();
    Project project = ProjectBuilder.builder()
        .build();
    plugin.apply(project);

  }
}
