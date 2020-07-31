package com.it2go.micro.projectmanagement.util;

import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.domain.ProjectStatus;
import com.it2go.micro.projectmanagement.domain.ProjectStep;
import com.it2go.micro.projectmanagement.domain.ProjectStepStatus;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectStepEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.UUID;

public class ProjectProducer {

    public static Project createProject() {
        Project project = Project.builder()
            .publicId(UUID.randomUUID())
            .name("New Building")
            .budget(500000.00)
            .description("New building as residential complex contain 8 flats")
            .planedStartDate(LocalDate.of(2020, Month.OCTOBER, 1))
            .planedFinishDate(LocalDate.of(2021, Month.OCTOBER, 31))
            .status(ProjectStatus.WAITING)
            .projectSteps(new ArrayList<>())
            .build();

        ProjectStep step1 = ProjectStep.builder()
            .publicId(UUID.randomUUID())
            .name("Ground work")
            .description("Prepare the ground and the building base")
            .budget(50000.00)
            .planedStartDate(LocalDate.of(2020, Month.OCTOBER, 1))
            .planedFinishDate(LocalDate.of(2020, Month.NOVEMBER, 30))
            .status(ProjectStepStatus.WAITING)
            .build();

        ProjectStep step2 = ProjectStep.builder()
            .publicId(UUID.randomUUID())
            .name("Build walls")
            .description("Build the walls and rooms")
            .budget(150000.00)
            .planedStartDate(LocalDate.of(2020, Month.DECEMBER, 1))
            .planedFinishDate(LocalDate.of(2020, Month.FEBRUARY, 28))
            .status(ProjectStepStatus.WAITING)
            .build();

        ProjectStep step3 = ProjectStep.builder()
            .publicId(UUID.randomUUID())
            .name("Build roof")
            .description("Build the roof")
            .budget(150000.00)
            .planedStartDate(LocalDate.of(2020, Month.MARCH, 1))
            .planedFinishDate(LocalDate.of(2020, Month.APRIL, 30))
            .status(ProjectStepStatus.WAITING)
            .build();

        project.getProjectSteps().add(step1);
        project.getProjectSteps().add(step2);
        project.getProjectSteps().add(step3);

        return project;
    }

    public static ProjectEntity createProjectEntity() {

        ProjectEntity project = ProjectEntity.builder()
            .id(1L)
            .publicId(UUID.randomUUID())
            .name("New Building")
            .budget(500000.00)
            .description("New building as residential complex contain 8 flats")
            .planedStartDate(LocalDate.of(2020, Month.OCTOBER, 1))
            .planedFinishDate(LocalDate.of(2021, Month.OCTOBER, 31))
            .status("WAITING")
            .projectSteps(new ArrayList<>())
            .build();

        ProjectStepEntity step1 = ProjectStepEntity.builder()
            .id(1L)
            .publicId(UUID.randomUUID())
            .name("Ground work")
            .description("Prepare the ground and the building base")
            .budget(50000.00)
            .planedStartDate(LocalDate.of(2020, Month.OCTOBER, 1))
            .planedFinishDate(LocalDate.of(2020, Month.NOVEMBER, 30))
            .status("WAITING")
            .build();

        ProjectStepEntity step2 = ProjectStepEntity.builder()
            .id(2L)
            .publicId(UUID.randomUUID())
            .name("Build walls")
            .description("Build the walls and rooms")
            .budget(150000.00)
            .planedStartDate(LocalDate.of(2020, Month.DECEMBER, 1))
            .planedFinishDate(LocalDate.of(2020, Month.FEBRUARY, 28))
            .status("WAITING")
            .build();

        ProjectStepEntity step3 = ProjectStepEntity.builder()
            .id(3L)
            .publicId(UUID.randomUUID())
            .name("Build roof")
            .description("Build the roof")
            .budget(150000.00)
            .planedStartDate(LocalDate.of(2020, Month.MARCH, 1))
            .planedFinishDate(LocalDate.of(2020, Month.APRIL, 30))
            .status("WAITING")
            .build();

        project.addProjectStep(step1);
        project.addProjectStep(step2);
        project.addProjectStep(step3);

        return project;
    }
}
