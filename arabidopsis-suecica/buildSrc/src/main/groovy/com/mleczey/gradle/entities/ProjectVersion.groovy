package com.mleczey.gradle.entities

import org.gradle.api.GradleException
import org.gradle.api.Project

class ProjectVersion {
  static final DEFAULT_VERSION_PROPERTIES_FILE_NAME = 'version.properties'

  Integer major
  Integer minor
  Integer maintenance
  Boolean release

  ProjectVersion(Integer major, Integer minor, Integer maintenance) {
    this.major = major
    this.minor = minor
    this.maintenance = maintenance
    this.release = false
  }

  ProjectVersion(Integer major, Integer minor, Integer maintenance, Boolean release) {
    this.major = major
    this.minor = minor
    this.maintenance = maintenance
    this.release = release
  }

  static getPropertiesFileName(Project project) {
    project.versionPropertiesFileName ?: DEFAULT_VERSION_PROPERTIES_FILE_NAME
  }

  static loadFromPropertiesFile(Project project) {
    File file = getPropertiesFile(project)

    Properties p = new Properties()
    file.withInputStream { stream ->
      p.load(stream)
    }

    new ProjectVersion(p.major.toInteger(), p.minor.toInteger(), p.maintenance.toInteger(), p.release.toBoolean())
  }

  static updatePropertiesFileWithRelease(Project project) {
    File file = getPropertiesFile(project)

    project.logger.quiet "updating release property with $project.version.release"

    project.ant.propertyfile(file: file) {
      entry (key: 'release', type: 'string', operation: '=', value: project.version.release.toString())
    }
  }

  static getPropertiesFile(Project project) {
    if (!project.versionPropertiesFileName) {
      project.logger.quiet 'version properties file name not defined, using default: $DEFAULT_VERSION_PROPERTIES_FILE_NAME'
    }

    File file = project.file(project.versionPropertiesFileName ?: DEFAULT_VERSION_PROPERTIES_FILE_NAME)

    if (!file.exists()) {
      throw new GradleException("file $file.canonicalPath is required to set proper application version, however it is missing")
    }

    return file
  }

  static updatePropertiesFileWithNumber(Project project, String key) {
    File file = getPropertiesFile(project)

    project.logger.quiet "updating $key property with ${project.version[key]}"

    project.ant.propertyfile(file: file) {
      entry (key: key, type: 'int', operation: '=', value: project.version[key])
    } 
  }

  @Override
  String toString() {
    "$major.$minor.$maintenance${->release ? '' : '-SNAPSHOT'}"
  }
}