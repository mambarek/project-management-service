package com.it2go.micro.projectmanagement.events;

import com.it2go.micro.projectmanagement.domain.Project;

public class NewProjectEvent extends ProjectEvent {

  public NewProjectEvent(Project project) {
    super(project);
  }
}
