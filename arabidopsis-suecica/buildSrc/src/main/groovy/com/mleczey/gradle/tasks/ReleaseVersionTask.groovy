package com.mleczey.gradle.tasks

import com.mleczey.gradle.entities.ProjectVersion
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class ReleaseVersionTask extends DefaultTask {
  @Input
  Boolean release
  @OutputFile
  File file

  ReleaseVersionTask() {
    group = 'versioning'
    description = 'executes project release' 
  }

  @TaskAction
  void run() {
    project.version.release = release
    ProjectVersion.updatePropertiesFileWithRelease(project)
  }
}