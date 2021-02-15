package com.it2go.micro.projectmanagement.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.projectmanagement.config.MessagingConfig;
import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.domain.ProjectStatus;
import com.it2go.micro.projectmanagement.domain.ProjectStep;
import com.it2go.micro.projectmanagement.domain.ProjectStepStatus;
import com.it2go.micro.projectmanagement.services.EmployeeService;
import com.it2go.micro.projectmanagement.services.ProjectService;
import com.it2go.micro.projectmanagement.services.messagin.MessageService;
import com.it2go.micro.projectmanagement.services.messagin.SendMessageException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProjectLoader implements CommandLineRunner {

  private final ProjectService projectService;
  private final MessageService messageService;
  private final ObjectMapper objectMapper;
  private final EmployeeService employeeService;
  // to be removed
  //private final RabbitTemplate rabbitTemplate;

  @Override
  public void run(String... args) {
    Project saveNewProject = projectService.saveNewProject(createProject());
    System.out.println(saveNewProject);
    projectService.saveNewProject(createProject2());
    projectService.saveNewProject(createProject3());
    try {
      startEmployeesImport();
    } catch (Exception e){
      System.out.println(e.getMessage());
    }

  }

  public static Project createProject() {
    Project project = Project.builder()
        .publicId(UUID.fromString("9a03a91d-8593-443f-a652-dd3a00dcfd81"))
        .name("New Building")
        .budget(500000.00)
        .description("New building as residential complex contain 8 flats")
        .planedStartDate(LocalDate.of(2020, Month.OCTOBER, 1))
        .planedFinishDate(LocalDate.of(2021, Month.OCTOBER, 31))
        .status(ProjectStatus.WAITING)
        .projectSteps(new ArrayList<>())
        .assignedEmployees(new ArrayList<>())
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

  public static Project createProject2() {
    Project project = Project.builder()
        .publicId(UUID.randomUUID())
        .name("Soccer stadium")
        .budget(50000000.00)
        .description("Soccer stadium for world cup 2026")
        .planedStartDate(LocalDate.of(2020, Month.FEBRUARY, 1))
        .planedFinishDate(LocalDate.of(2026, 1, 31))
        .status(ProjectStatus.WAITING)
        .projectSteps(new ArrayList<>())
        .assignedEmployees(new ArrayList<>())
        .build();

    ProjectStep step1 = ProjectStep.builder()
        .publicId(UUID.randomUUID())
        .name("Ground work")
        .description("Prepare the ground and the stadium base")
        .budget(1500000.00)
        .planedStartDate(LocalDate.of(2020, 2, 15))
        .planedFinishDate(LocalDate.of(2020, Month.AUGUST, 30))
        .status(ProjectStepStatus.WAITING)
        .build();

    ProjectStep step2 = ProjectStep.builder()
        .publicId(UUID.randomUUID())
        .name("Build walls")
        .description("Build the walls and rooms")
        .budget(1500000.00)
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

  public static Project createProject3() {
    Project project = Project.builder()
        .publicId(UUID.randomUUID())
        .name("Airport Chicago")
        .budget(300000000.00)
        .description("The new Airport of Chicago")
        .planedStartDate(LocalDate.of(2020, Month.FEBRUARY, 1))
        .planedFinishDate(LocalDate.of(2026, 1, 31))
        .status(ProjectStatus.WAITING)
        .projectSteps(new ArrayList<>())
        .assignedEmployees(new ArrayList<>())
        .build();

    ProjectStep step1 = ProjectStep.builder()
        .publicId(UUID.randomUUID())
        .name("Ground work")
        .description("Prepare the ground and the stadium base")
        .budget(1500000.00)
        .planedStartDate(LocalDate.of(2020, 2, 15))
        .planedFinishDate(LocalDate.of(2020, Month.AUGUST, 30))
        .status(ProjectStepStatus.WAITING)
        .build();

    ProjectStep step2 = ProjectStep.builder()
        .publicId(UUID.randomUUID())
        .name("Build walls")
        .description("Build the walls and rooms")
        .budget(1500000.00)
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

  void startEmployeesImport() throws SendMessageException {
    messageService.sendMessage(MessagingConfig.EMPLOYEES_IMPORT_QUEUE, "Start Employees import!");
  }

}
