package com.it2go.micro.projectmanagement.events;

import com.it2go.micro.projectmanagement.domain.Project;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class ProjectChangedEvent extends ProjectEvent {


  public List<UUID> removedEmployees;

  public ProjectChangedEvent(Project project) {
    super(project);
  }

}
