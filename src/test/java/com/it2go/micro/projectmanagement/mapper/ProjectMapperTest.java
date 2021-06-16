package com.it2go.micro.projectmanagement.mapper;

import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.util.ProjectProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMapperTest {

    ProjectMapper projectMapper;

    @BeforeEach
    void beforeEach(){
        projectMapper = new ProjectMapperImpl();
    }

    @Test
    void testProjectEntityToProject() {
        ProjectEntity projectEntity = projectMapper
            .projectToProjectEntity(ProjectProducer.createProject());
        System.out.println(projectEntity);
    }

    @Test
    void testProjectToProjectEntity() {
        ProjectEntity projectEntity = ProjectProducer.createProjectEntity();
        Project project = projectMapper.projectEntityToProject(projectEntity);
        System.out.println(project);
    }

    @Test
    void testUpdateProjectEntity() {
        Project project = ProjectProducer.createProject();
        project.setBudget(255000.00);
        project.setPlanedStartDate(LocalDate.of(2020, Month.JULY, 1));
        ProjectEntity projectEntity = ProjectProducer.createProjectEntity();
        ProjectEntity updateProjectEntity = projectMapper
            .updateProjectEntity(projectEntity, project);

        assertNotNull(updateProjectEntity);
        assertEquals(updateProjectEntity.getBudget(), 255000.00);
    }
}
