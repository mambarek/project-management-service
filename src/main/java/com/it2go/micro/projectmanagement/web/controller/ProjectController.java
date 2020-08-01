package com.it2go.micro.projectmanagement.web.controller;

import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.services.ProjectService;
import java.net.URI;
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

  @GetMapping("/{publicId}")
  public ResponseEntity<Project> getProjectByPublicId(
      @PathVariable("publicId") @NotNull UUID publicId) {
    Project project = projectService.findProjectByPublicId(publicId);

    return new ResponseEntity<>(project, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Project> saveProject(@RequestBody @Valid Project project) {
    Project savedProject = projectService.saveNewProject(project);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{publicId}")
        .buildAndExpand(savedProject.getPublicId()).toUri();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);

    return new ResponseEntity<>(savedProject, responseHeaders, HttpStatus.CREATED);
  }

  @PutMapping("/{publicId}")
  public ResponseEntity<Project> updateProject(@RequestBody @Valid Project project) {
    Project updatedProject = projectService.updateProject(project);

    return new ResponseEntity<>(updatedProject, HttpStatus.OK);
  }
}
