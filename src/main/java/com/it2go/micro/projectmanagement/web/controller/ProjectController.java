package com.it2go.micro.projectmanagement.web.controller;

import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.services.ProjectService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @ApiOperation(
      nickname = "Retrieves all projects nick",
      value = "Retrieves all projects value",
      notes = "A list of all projects",
      response = List.class,
      responseContainer = "ResponseEntity",
      produces = "application/json")
  @GetMapping
  public ResponseEntity<List<Project>> getAllProjects() {
    List<Project> allProjects = projectService.findAllProjects();

    return new ResponseEntity<>(allProjects, HttpStatus.OK);
  }

  @ApiOperation(
      nickname = "Retrieves a project by public id",
      value = "Retrieves a project by public id",
      notes = "Returns the projects with public id",
      response = Project.class,
      responseContainer = "ResponseEntity",
      produces = "application/json",
      consumes = "application/json"
  )
  @GetMapping("/{publicId}")
  public ResponseEntity<Project> getProjectByPublicId(
      @PathVariable("publicId") @NotNull UUID publicId) {
    Project project = projectService.findProjectByPublicId(publicId);

    return new ResponseEntity<>(project, HttpStatus.OK);
  }

  @ApiOperation(
      nickname = "Saves a new project",
      value = "Saves a new project",
      notes = "Returns the saved project",
      response = Project.class,
      responseContainer = "ResponseEntity",
      produces = "application/json",
      consumes = "application/json"
  )
  @PostMapping
  public ResponseEntity<Project> saveProject(@RequestBody @Valid Project project) {
    Project savedProject = projectService.saveNewProject(project);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{publicId}")
        .buildAndExpand(savedProject.getPublicId()).toUri();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);

    return new ResponseEntity<>(savedProject, responseHeaders, HttpStatus.CREATED);
  }

  @ApiOperation(
      nickname = "Updates a project with a public id",
      value = "Updates a project with a public id",
      notes = "Returns the updated project",
      response = Project.class,
      responseContainer = "ResponseEntity",
      produces = "application/json",
      consumes = "application/json"
  )
  @PutMapping("/{publicId}")
  public ResponseEntity<Project> updateProject(@RequestBody @Valid Project project) {
    Project updatedProject = projectService.updateProject(project);

    return new ResponseEntity<>(updatedProject, HttpStatus.OK);
  }

  @ApiOperation(
      nickname = "Returns the count of all projects",
      value = "Returns the count of all projects",
      notes = "Returns the project count",
      response = Long.class,
      responseContainer = "ResponseEntity",
      produces = "application/json",
      consumes = "application/json"
  )
  @GetMapping("/count")
  public ResponseEntity<Long> getProjectsCount() {
    return new ResponseEntity<>(projectService.countProjects(), HttpStatus.OK);
  }
}
