package com.it2go.micro.projectmanagement.services.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.services.ProjectService;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProjectEventListener {

  //private final JmsTemplate jmsTemplate;
  private final ProjectService projectService;
  private final ObjectMapper objectMapper;

  //@Transactional // why Transaction
  // JmsListener is async and running on a different thread. so it has no hibernate session in this thread
  // with @Transactional you start e new Transaction with a new session
  //@JmsListener(destination = "PROJECT_REQUEST_QUEUE")
  public void listenToProjectEvent(@Payload String projectPublicId, @Headers MessageHeaders headers,
      Message message) throws JMSException {
    log.info("-- listenToProjectEvent: " + projectPublicId);
    Project projectByPublicId = projectService
        .findProjectByPublicId(UUID.fromString(projectPublicId));
    log.info("--listenToProjectEvent project found " + projectByPublicId);

    try {
      String valueAsString = objectMapper.writeValueAsString(projectByPublicId);
      //jmsTemplate.convertAndSend(message.getJMSReplyTo(), valueAsString);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

  }

}