package com.it2go.micro.projectmanagement.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.projectmanagement.ProjectManagementApplication;
import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity_;
import com.it2go.micro.projectmanagement.search.ProjectTableItem;
import com.it2go.util.jpa.search.Group;
import com.it2go.util.jpa.search.GroupOperation;
import com.it2go.util.jpa.search.Operation;
import com.it2go.util.jpa.search.Rule;
import com.it2go.util.jpa.search.RuleType;
import com.it2go.util.jpa.search.SearchResult;
import com.it2go.util.jpa.search.SearchTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(classes = ProjectManagementApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProjectControllerIT {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  String newProjectName = "New Google App";

  @Test
  void testSearch() {
    System.out.println("-- testSearch project BEGIN");

    // search for project with name contain "Building"
    Rule rule1 = new Rule();
    rule1.setField(ProjectEntity_.name.getName());
    rule1.setData("Building");
    rule1.setOp(Operation.CONTAINS);
    rule1.setType(RuleType.STRING);

    // or with name newProjectName value
    Rule rule2 = new Rule();
    rule2.setField(ProjectEntity_.name.getName());
    rule2.setData(newProjectName);
    rule2.setOp(Operation.EQUAL);
    rule2.setType(RuleType.STRING);

    // the project name may be changed executing testUpdateProject() test
    // so search too for the new name

    Group group = new Group();
    group.setGroupOp(GroupOperation.OR);
    group.getRules().add(rule1);
    group.getRules().add(rule2);

    SearchTemplate employeesSearchTemplate = new SearchTemplate();
    employeesSearchTemplate.setFilters(group);

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      System.out.println(objectMapper.writeValueAsString(employeesSearchTemplate));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    SearchResult searchResult = restTemplate
        .postForObject("http://localhost:" + port + "/api/v1/projects/search",
            employeesSearchTemplate, SearchResult.class);

    System.out.println(searchResult);
    assertNotNull(searchResult.getRows());
    assertEquals(searchResult.getRows().size(), 1);
    System.out.println(searchResult);
    System.out.println("-- testSearch project END");
  }

  @Test
  void testUpdateProject() {
    System.out.println("-- testUpdateProject BEGIN");
    String baseUrl = "http://localhost:" + port + "/api/v1/projects";
    Project[] projects = restTemplate
        .getForObject(baseUrl, Project[].class);

    Project project1 = projects[0];
    System.out.println(project1);
    String projectPath = baseUrl + "/" + project1.getPublicId();

    System.out.println("-->> Step 2");
    project1.setName("New Google App");

    restTemplate.patchForObject(projectPath, project1, Project.class);

    //System.out.println(updatedProject);
    System.out.println("-->> Step 3");
    Project project2 = restTemplate
        .getForObject(projectPath, Project.class);

    System.out.println(project2);
    assertEquals(project2.getName(), "New Google App");
    System.out.println("-- testUpdateProject END");
  }
}
