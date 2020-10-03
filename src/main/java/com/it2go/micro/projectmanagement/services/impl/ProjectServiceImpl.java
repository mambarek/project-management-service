package com.it2go.micro.projectmanagement.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectMapper projectMapper;
  private final ProjectRepository projectRepository;
  //private final JmsTemplate jmsTemplate;
  private final ObjectMapper objectMapper;

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
    log.info(String.format("-- saveNewProject: [%s]", project.getPublicId()));
    ProjectEntity projectEntity = projectMapper.projectToProjectEntity(project);
    ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
    Project savedProject = projectMapper.projectEntityToProject(savedProjectEntity);

    log.info(String.format("-- saveNewProject: [%s] saved successfully", project.getPublicId()));
    log.info(String.format("-- saveNewProject: [%s] send creation event", project.getPublicId()));

    //jmsTemplate.convertAndSend(JmsConfig.NEW_PROJECTS_QUEUE, savedProject);

    try {
      String valueAsString = objectMapper.writeValueAsString(savedProject);
      MessageCreator mc = s -> s.createTextMessage(valueAsString);
      //jmsTemplate.send(JmsConfig.NEW_PROJECTS_QUEUE, mc);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return savedProject;
  }

  @Override
  public Project updateProject(Project project) {
    log.info(String.format("-- updateProject: [%s]", project.getPublicId()));
    ProjectEntity byPublicId = projectRepository.findByPublicId(project.getPublicId())
        .orElseThrow(EntityNotFoundException::new);

    ProjectEntity updatedProjectEntity = projectMapper.updateProjectEntity(byPublicId, project);
    ProjectEntity savedProjectEntity = projectRepository.save(updatedProjectEntity);
    Project updatedProject = projectMapper.projectEntityToProject(savedProjectEntity);
    log.info(String.format("-- updateProject: [%s] updated successfully", project.getPublicId()));
    log.info(String.format("-- updateProject: [%s] send update event", project.getPublicId()));
    // send event of update
    //jmsTemplate.convertAndSend(JmsConfig.PROJECTS_CHANGED_QUEUE, updatedProject);

    return updatedProject;
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
