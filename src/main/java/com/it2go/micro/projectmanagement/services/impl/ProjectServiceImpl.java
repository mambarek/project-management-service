package com.it2go.micro.projectmanagement.services.impl;

import com.it2go.micro.employeesservice.domain.Employee;
import com.it2go.micro.projectmanagement.config.MessagingConfig;
import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.domain.ProjectStatus;
import com.it2go.micro.projectmanagement.mapper.EmployeeMapper;
import com.it2go.micro.projectmanagement.mapper.ProjectMapper;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.persistence.jpa.repositories.EmployeeRepository;
import com.it2go.micro.projectmanagement.persistence.jpa.repositories.ProjectRepository;
import com.it2go.micro.projectmanagement.services.EntityNotFoundException;
import com.it2go.micro.projectmanagement.services.ProjectService;
import com.it2go.micro.projectmanagement.services.messagin.MessageService;
import com.it2go.micro.projectmanagement.services.messagin.SendMessageException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectMapper projectMapper;
  private final ProjectRepository projectRepository;
  private final EmployeeRepository employeeRepository;
  private final MessageService messageService;

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
    if(project.getPublicId() == null)
        project.setPublicId(UUID.randomUUID());

    project.setStatus(ProjectStatus.WAITING);
    if (project.getProjectSteps() == null) project.setProjectSteps(new ArrayList<>());

    log.info(String.format("-- saveNewProject: [%s]", project.getPublicId()));
    ProjectEntity projectEntity = projectMapper.projectToProjectEntity(project);

    List<EmployeeEntity> employeeEntities = new ArrayList<>();
    for (Employee employee: project.getAssignedEmployees()){
      EmployeeEntity employeeEntity = employeeRepository.findByPublicId(employee.getPublicId())
          .orElseThrow(EntityNotFoundException::new);
      employeeEntities.add(employeeEntity);
    }
    projectEntity.setAssignedEmployees(employeeEntities);
    ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
    Project savedProject = projectMapper.projectEntityToProject(savedProjectEntity);

    log.info(String.format("-- saveNewProject: [%s] saved successfully", project.getPublicId()));
    log.info(String.format("-- saveNewProject: [%s] send creation event", project.getPublicId()));

    try {
      messageService.sendMessage(MessagingConfig.NEW_PROJECTS_QUEUE, savedProject);
    } catch (SendMessageException e) {
      log.error("Error while sending message for saved project");
    }

/*    try {
      String valueAsString = objectMapper.writeValueAsString(savedProject);
      MessageCreator mc = s -> s.createTextMessage(valueAsString);
      jmsTemplate.send(JmsConfig.NEW_PROJECTS_QUEUE, mc);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }*/

    return savedProject;
  }

  @Override
  public Project updateProject(Project project) {
    log.info(String.format("-- updateProject: [%s]", project.getPublicId()));
    ProjectEntity byPublicId = projectRepository.findByPublicId(project.getPublicId())
        .orElseThrow(EntityNotFoundException::new);

    List<EmployeeEntity> employeeEntities = new ArrayList<>();
    for (Employee employee: project.getAssignedEmployees()){
      EmployeeEntity employeeEntity = employeeRepository.findByPublicId(employee.getPublicId())
          .orElseThrow(EntityNotFoundException::new);
      employeeEntities.add(employeeEntity);
    }

    byPublicId.setAssignedEmployees(employeeEntities);
    ProjectEntity updatedProjectEntity = projectMapper.updateProjectEntity(byPublicId, project);
    ProjectEntity savedProjectEntity = projectRepository.save(updatedProjectEntity);
    Project updatedProject = projectMapper.projectEntityToProject(savedProjectEntity);
    log.info(String.format("-- updateProject: [%s] updated successfully", project.getPublicId()));
    log.info(String.format("-- updateProject: [%s] send update event", project.getPublicId()));
    // send event of update
    try {
      messageService.sendMessage(MessagingConfig.UPDATED_PROJECTS_QUEUE, updatedProject);
    } catch (SendMessageException e) {
      log.error("Error while sending message for update project", e);
    }

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
