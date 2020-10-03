package com.it2go.micro.projectmanagement.events;

import com.it2go.micro.projectmanagement.domain.Project;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class ProjectEvent implements Serializable {

  static final long serialVersionUID = 4643765427080172119L;

  private final Project project;
}
