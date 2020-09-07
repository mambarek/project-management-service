package com.it2go.micro.projectmanagement.web.controller;

import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.search.ProjectTableItem;
import com.it2go.micro.projectmanagement.services.ProjectSearchService;
import com.it2go.micro.projectmanagement.services.ProjectService;
import com.it2go.util.jpa.search.SearchResult;
import com.it2go.util.jpa.search.SearchTemplate;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        contact = @Contact(url = "", name = "Mohamed Ali Mbarek", email = "mbarek@it-2go.de")
    )
)
@Tags(value = {@Tag(name = "Projects", description = "Projects Management Application")})
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

  private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

  private final ProjectService projectService;
  private final ProjectSearchService projectSearchService;

  @Operation(operationId = "getAllProjects", summary = "Get projects",
      description = "Get list of projects"
  )
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "Projects successful found",
              content = @Content(schema = @Schema(implementation = Project[].class))
          )
      }
  )

  @GetMapping
  public ResponseEntity<List<Project>> getAllProjects() {
    LOG.info("-- getAllProjects() retrieve all Projects");
    List<Project> allProjects = projectService.findAllProjects();

    return new ResponseEntity<>(allProjects, HttpStatus.OK);
  }

  @Operation(summary = "Retrieves a project by public id", operationId = "getProjectByPublicId",
      description = "Retrieves a project by public it's id",
      parameters = {@Parameter(name = "publicId", description = "The project publicId")}
  )
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "Project retrieved successfully",
              content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))}),
          @ApiResponse(responseCode = "400", description = "Project not found", content = {})
      }
  )
  @GetMapping("/{publicId}")
  public ResponseEntity<Project> getProjectByPublicId(
      @PathVariable("publicId") @NotNull UUID publicId) {

    LOG.info(String.format("-- getProjectByPublicId(%s)", publicId));
    Project project = projectService.findProjectByPublicId(publicId);

    if (project == null) {
      return ResponseEntity.notFound().build();
    }

    return new ResponseEntity<>(project, HttpStatus.OK);
  }

  @Operation(summary = "Saves a new project",
      description = "Saves a new project")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "201", description = "Project saved/created successfully",
              content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))})}
  )
  @PostMapping
  public ResponseEntity<Project> saveProject(@RequestBody @Valid Project project) {
    LOG.info(String.format("-- saveProject() project.publicId: [%s]", project.getPublicId()));
    Project savedProject = projectService.saveNewProject(project);
    LOG.info(String.format("-- saveProject() project.publicId: [%s] successfully saved",
        project.getPublicId()));
    // create location url and set it in header
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{publicId}")
        .buildAndExpand(savedProject.getPublicId()).toUri();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);

    return new ResponseEntity<>(savedProject, responseHeaders, HttpStatus.CREATED);
  }

  @Operation(summary = "Updates a project with a public id",
      description = "Updates a project with a public id")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "Project updated successfully",
              content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))}),
          @ApiResponse(responseCode = "400", description = "Bad request / wrong publicId"),
          @ApiResponse(responseCode = "500", description = "Backend error")
      }
  )
  @PatchMapping("/{publicId}")
  public ResponseEntity<Project> updateProject(@RequestBody @Valid Project project,
      @PathVariable("publicId") @NotNull UUID publicId) {
    LOG.info(String.format("-- updateProject() [%s]", publicId));

    if (!publicId.equals(project.getPublicId())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Project updatedProject = projectService.updateProject(project);

    return new ResponseEntity<>(updatedProject, HttpStatus.OK);
  }

  @Operation(summary = "Delete a project with a public id",
      description = "Delete a project with a public id")
  @DeleteMapping("/{publicId}")
  public ResponseEntity<Void> deleteProject(@PathVariable("publicId") @NotNull UUID publicId) {
    LOG.info(String.format("-- deleteProject(%s)", publicId));
    projectService.deleteProject(publicId);

    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Returns the count of all projects",
      description = "Returns the count of all projects")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)))})
  @GetMapping("/count")
  public ResponseEntity<Long> getProjectsCount() {
    LOG.info("-- getProjectsCount()");
    return new ResponseEntity<>(projectService.countProjects(), HttpStatus.OK);
  }

  @Operation(summary = "Search projects for a given filter",
      description = "Return a SearchResult containing found projects")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectTableItem[].class)))})
  @PostMapping("/search")
  public ResponseEntity<SearchResult<ProjectTableItem>> search(
      @RequestBody @NotNull SearchTemplate searchTemplate) {
    List<ProjectTableItem> projectTableItems = projectSearchService.filterProjects(searchTemplate);
    SearchResult<ProjectTableItem> searchResult = new SearchResult<>();
    searchResult.setRows(projectTableItems);

    return ResponseEntity.ok(searchResult);
  }
}
