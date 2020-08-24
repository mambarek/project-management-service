package com.it2go.micro.projectmanagement.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.projectmanagement.ProjectManagementApplication;
import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity_;
import com.it2go.micro.projectmanagement.search.ProjectTableItem;
import com.it2go.util.jpa.search.Group;
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

  @Test
  void testSearch() {
    Rule rule = new Rule();
    rule.setField(ProjectEntity_.name.getName());
    //rule.setData("New Building");
    rule.setData("Building");
    rule.setOp(Operation.CONTAINS);
    rule.setType(RuleType.STRING);
    Group group = new Group();
    group.getRules().add(rule);

    SearchTemplate employeesSearchTemplate = new SearchTemplate();
    employeesSearchTemplate.setFilters(group);

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      System.out.println(objectMapper.writeValueAsString(employeesSearchTemplate));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    SearchResult<ProjectTableItem> searchResult = restTemplate
        .postForObject("http://localhost:" + port + "/api/v1/projects/search",
            employeesSearchTemplate, SearchResult.class);

    assertEquals(searchResult.getRows().size(), 1);
    System.out.println(searchResult);
  }

  @Test
  void testUpdateProject() {
    String baseUrl = "http://localhost:" + port + "/api/v1/projects";
    Project[] projects = restTemplate
        .getForObject(baseUrl, Project[].class);

    System.out.println(projects[0]);
    Project project1 = projects[0];
    System.out.println("-->> Step 2");
    project1.setName("New Google App");
    restTemplate
        .put(baseUrl + "/" + project1.getPublicId(), project1);

    //System.out.println(updatedProject);
    System.out.println("-->> Step 3");
    Project[] projects2 = restTemplate
        .getForObject(baseUrl, Project[].class);

    System.out.println(projects2[0]);
  }
}
