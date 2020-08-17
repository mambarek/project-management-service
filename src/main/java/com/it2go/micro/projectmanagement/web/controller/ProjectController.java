package com.it2go.micro.projectmanagement.web.controller;

import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.search.ProjectTableItem;
import com.it2go.micro.projectmanagement.search.SearchResultResponse;
import com.it2go.micro.projectmanagement.services.ProjectSearchService;
import com.it2go.micro.projectmanagement.services.ProjectService;
import com.it2go.util.jpa.search.SearchTemplate;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@OpenAPIDefinition(
    info = @Info(
        title = "Project Management Service API",
        description = "API for Project management App",
        version = "1.0",
        license = @License(name = "Apache license 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
        contact = @Contact(url = "http://gigantic-server.com", name = "Mohamed Ali Mbarek", email = "mbarek@it-2go.de")
    )
)
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;
  private final ProjectSearchService projectSearchService;

  @Operation(summary = "Get projects",
      description = "Get list of projects")
  @GetMapping
  public ResponseEntity<List<Project>> getAllProjects() {
    List<Project> allProjects = projectService.findAllProjects();

    return new ResponseEntity<>(allProjects, HttpStatus.OK);
  }

  @Operation(summary = "Retrieves a project by public id",
      description = "Retrieves a project by public id")
  @GetMapping("/{publicId}")
  public ResponseEntity<Project> getProjectByPublicId(
      @PathVariable("publicId") @NotNull UUID publicId) {
    Project project = projectService.findProjectByPublicId(publicId);

    return new ResponseEntity<>(project, HttpStatus.OK);
  }

  @Operation(summary = "Saves a new project",
      description = "Saves a new project")
  @PostMapping
  public ResponseEntity<Project> saveProject(@RequestBody @Valid Project project) {
    Project savedProject = projectService.saveNewProject(project);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{publicId}")
        .buildAndExpand(savedProject.getPublicId()).toUri();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);

    return new ResponseEntity<>(savedProject, responseHeaders, HttpStatus.CREATED);
  }

  @Operation(summary = "Updates a project with a public id",
      description = "Updates a project with a public id")
  @PatchMapping("/{publicId}")
  public ResponseEntity<Project> updateProject(@RequestBody @Valid Project project,
      @PathVariable("publicId") @NotNull UUID publicId) {

    if (!publicId.equals(project.getPublicId())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Project updatedProject = projectService.updateProject(project);

    return new ResponseEntity<>(updatedProject, HttpStatus.OK);
  }

  @Operation(summary = "Returns the count of all projects",
      description = "Returns the count of all projects")
  @GetMapping("/count")
  public ResponseEntity<Long> getProjectsCount() {
    return new ResponseEntity<>(projectService.countProjects(), HttpStatus.OK);
  }

  @Operation(summary = "Search projects for a given filter",
      description = "Return a SearchResultResponse containing found projects")
  @PostMapping("/search")
  public ResponseEntity<SearchResultResponse> search(
      @RequestBody @NotNull SearchTemplate searchTemplate) {
    List<ProjectTableItem> projectTableItems = projectSearchService.filterProjects(searchTemplate);
    SearchResultResponse searchResultResponse = new SearchResultResponse(projectTableItems);
    return ResponseEntity.ok(searchResultResponse);
  }
}
