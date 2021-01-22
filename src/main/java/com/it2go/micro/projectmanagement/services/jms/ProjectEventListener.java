package com.it2go.micro.projectmanagement.services.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.events.ProjectExportEvent;
import com.it2go.micro.projectmanagement.services.ProjectService;
import java.util.List;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProjectEventListener {

  private final JmsService jmsService;
  private final ProjectService projectService;
  private final ObjectMapper objectMapper;

  @Transactional // why Transaction
  // JmsListener is async and running on a different thread. so it has no hibernate session in this thread
  // with @Transactional you start e new Transaction with a new session
  @JmsListener(destination = "PROJECT_REQUEST_QUEUE")
  public void listenToProjectEvent(String projectPublicId) throws Exception {
    log.info("-- listenToProjectEvent: " + projectPublicId);
    Project projectByPublicId = projectService
        .findProjectByPublicId(UUID.fromString(projectPublicId));
    log.info("--listenToProjectEvent project found " + projectByPublicId);

    jmsService.sendMessage("PROJECT_RESPONSE_QUEUE", projectByPublicId);
/*    try {
      String valueAsString = objectMapper.writeValueAsString(projectByPublicId);
      jmsTemplate.convertAndSend(message.getJMSReplyTo(), valueAsString);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }*/

  }

/*  @Transactional // lazy load from hibernate exception
  @JmsListener(destination = "PROJECT_IMPOR_QUEUE")
  public void exportAllProjects(){
    List<Project> allProjects = projectService.findAllProjects();
    ProjectExportEvent projectExportEvent = new ProjectExportEvent(allProjects);
    jmsTemplate.convertAndSend("PROJECT_EXPORT_QUEUE", projectExportEvent);
  }*/

  @Transactional // lazy load from hibernate exception
  @JmsListener(destination = "PROJECT_IMPORT_QUEUE")
  public void exportAllProjectsJson(){
    List<Project> allProjects = projectService.findAllProjects();
    ProjectExportEvent projectExportEvent = new ProjectExportEvent(allProjects);
    //jmsTemplate.convertAndSend("PROJECT_EXPORT_QUEUE", projectExportEvent);

    try {
      String valueAsString = objectMapper.writeValueAsString(projectExportEvent);
      jmsService.sendMessage("PROJECT_EXPORT_QUEUE", valueAsString);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
