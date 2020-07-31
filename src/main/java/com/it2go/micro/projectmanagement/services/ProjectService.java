package com.it2go.micro.projectmanagement.services;

import com.it2go.micro.projectmanagement.domain.Project;
import java.util.UUID;

public interface ProjectService {

  Project findProjectByPublicId(UUID publicId);

  Project saveNewProject(Project project);

  Project updateProject(Project project);

  Long countProjects();
}
