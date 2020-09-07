package com.it2go.micro.projectmanagement.services.impl;

import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.mapper.ProjectMapper;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.persistence.jpa.repositories.ProjectRepository;
import com.it2go.micro.projectmanagement.services.EntityNotFoundException;
import com.it2go.micro.projectmanagement.services.ProjectService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectMapper projectMapper;
  private final ProjectRepository projectRepository;

  @Override
  public List<Project> findAllProjects() {
    List<Project> result = new ArrayList<>();
    projectRepository.findAll()
        .forEach(projectEntity -> result.add(projectMapper.projectEntityToProject(projectEntity)));
    return result;
  }

  @Override
  public Project findProjectByPublicId(UUID publicId) {
    ProjectEntity projectEntity = projectRepository.findByPublicId(publicId).orElse(null);
    if (projectEntity == null) {
      return null;
    }
    return projectMapper.projectEntityToProject(projectEntity);
  }

  @Override
  public Project saveNewProject(Project project) {
    ProjectEntity projectEntity = projectMapper.projectToProjectEntity(project);
    ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);

    return projectMapper.projectEntityToProject(savedProjectEntity);
  }

  @Override
  public Project updateProject(Project project) {
    ProjectEntity byPublicId = projectRepository.findByPublicId(project.getPublicId())
        .orElseThrow(EntityNotFoundException::new);

    ProjectEntity updatedProjectEntity = projectMapper.updateProjectEntity(byPublicId, project);
    ProjectEntity savedProjectEntity = projectRepository.save(updatedProjectEntity);

    return projectMapper.projectEntityToProject(savedProjectEntity);
  }

  @Override
  public void deleteProject(UUID publicId) {
    ProjectEntity byPublicId = projectRepository.findByPublicId(publicId)
        .orElseThrow(EntityNotFoundException::new);
    projectRepository.delete(byPublicId);
  }

  @Override
  public Long countProjects() {
    return projectRepository.count();
  }
}
