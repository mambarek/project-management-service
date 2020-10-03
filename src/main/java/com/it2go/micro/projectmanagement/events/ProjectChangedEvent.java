package com.it2go.micro.projectmanagement.events;

import com.it2go.micro.projectmanagement.domain.Project;

public class ProjectChangedEvent extends ProjectEvent {

  public ProjectChangedEvent(Project project) {
    super(project);
  }
}
