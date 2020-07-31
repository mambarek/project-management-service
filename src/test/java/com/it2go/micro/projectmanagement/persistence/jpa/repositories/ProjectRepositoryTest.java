package com.it2go.micro.projectmanagement.persistence.jpa.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.mapper.ProjectMapper;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.util.ProjectProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectRepositoryTest {

  @Autowired
  ProjectRepository projectRepository;

  @Autowired
  ProjectMapper projectMapper;

  @Test
  void testCreate() {
    Project project = ProjectProducer.createProject();
    ProjectEntity projectEntity = projectMapper.projectToProjectEntity(project);

    ProjectEntity savedProject = projectRepository.save(projectEntity);
    assertNotNull(savedProject.getId());
    assertEquals(savedProject.getPublicId(), project.getPublicId());
  }
}
