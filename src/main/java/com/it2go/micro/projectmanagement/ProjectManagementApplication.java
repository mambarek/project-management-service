package com.it2go.micro.projectmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
public class ProjectManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProjectManagementApplication.class, args);
  }

}
