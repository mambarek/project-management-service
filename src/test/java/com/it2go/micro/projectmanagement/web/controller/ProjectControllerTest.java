package com.it2go.micro.projectmanagement.web.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.services.ProjectSearchService;
import com.it2go.micro.projectmanagement.services.ProjectService;
import com.it2go.micro.projectmanagement.util.ProjectProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  ProjectService projectService;
  @MockBean
  ProjectSearchService projectSearchService;

  @Test
  void getProjectByPublicId() throws Exception {
    Project project = ProjectProducer.createProject();
    // mock the service
    when(projectService.findProjectByPublicId(any())).thenReturn(project);

    MvcResult getResult = mockMvc.perform(get("/api/v1/projects/{publicId}", project.getPublicId())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.publicId").value(project.getPublicId().toString()))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void saveProject() throws Exception {
    Project project = ProjectProducer.createProject();
    String projectJson = objectMapper.writeValueAsString(project);

    when(projectService.saveNewProject(any())).thenReturn(project);

    MvcResult mvcResult = mockMvc.perform(post("/api/v1/projects")
        .contentType(MediaType.APPLICATION_JSON)
        .content(projectJson))
        .andExpect(jsonPath("$.publicId").value(project.getPublicId().toString()))
        .andExpect(header().exists("Location"))
        .andExpect(status().isCreated()).andReturn();

    String uri = mvcResult.getResponse().getHeader("Location");
    assertNotNull(uri);
    assertTrue(uri.contains(project.getPublicId().toString()));

    System.out.println(uri);

    String jsonProject = mvcResult.getResponse().getContentAsString();
    assertNotNull(jsonProject);
    assertTrue(jsonProject.length() > 0);

    System.out.println(jsonProject);
  }

  @Test
  void updateProject() throws Exception {
    Project project = ProjectProducer.createProject();
    String projectJson = objectMapper.writeValueAsString(project);

    when(projectService.updateProject(any())).thenReturn(project);

    MvcResult mvcResult = mockMvc.perform(put("/api/v1/projects/{publicId}", project.getPublicId())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(projectJson))
        .andExpect(jsonPath("$.publicId").value(project.getPublicId().toString()))
        .andExpect(status().isOk()).andReturn();
  }

}
