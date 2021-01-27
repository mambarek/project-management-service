package com.it2go.micro.projectmanagement.config;

/**
 * created by mmbarek on 24.01.2021.
 */
public class MessagingConfig {

  public static final String NEW_PROJECTS_QUEUE = "cma.new.projects";
  public static final String UPDATED_PROJECTS_QUEUE = "cma.updated.projects";

  public static final String NEW_EMPLOYEES_QUEUE = "cma.new.employees";
  public static final String UPDATED_EMPLOYEES_QUEUE = "cma.updated.employees";

  public static final String EMPLOYEES_IMPORT_QUEUE = "cma.employees.import";
  public static final String EMPLOYEES_EXPORT_QUEUE = "cma.employees.export";
}
