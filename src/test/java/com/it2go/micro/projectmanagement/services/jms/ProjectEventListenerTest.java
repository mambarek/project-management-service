package com.it2go.micro.projectmanagement.services.jms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * created by mmbarek on 29.12.2020.
 */
@SpringBootTest
class ProjectEventListenerTest {

  @Autowired
  JmsTemplate jmsTemplate;

  @Test
  void listenToProjectEvent() {
    //try {
      String pubId = "9a03a91d-8593-443f-a652-dd3a00dcfd81";
      MessageCreator mc = session -> session
          .createTextMessage(pubId);

      System.out.println("-- getProjectByPublicId() send message project publicId: " + pubId);
      //Message message = jmsTemplate.sendAndReceive("PROJECT_REQUEST_QUEUE", mc);

    jmsTemplate.convertAndSend("PROJECT_REQUEST_QUEUE", pubId);
      System.out.println("-- getProjectByPublicId() response back ");
      //assert message != null;
      //String projectJson = message.getBody(String.class);
      //System.out.println("-- getProjectByPublicId() response in json: " + projectJson);
    Object project = jmsTemplate.receiveAndConvert("PROJECT_RESPONSE_QUEUE");
    System.out.println(String.format("-- getProjectByPublicId() response in json: %s" , project));
  }

  @Test
  void importProject() {
    jmsTemplate.convertAndSend("PROJECT_IMPOR_QUEUE", "");
    Object projects = jmsTemplate.receiveAndConvert("PROJECT_EXPORT_QUEUE");
    System.out.println("-- All Projects");
    System.out.println(projects);
  }

  @Test
  void importProjectJson() {
    jmsTemplate.convertAndSend("PROJECT_IMPOR_QUEUE", "");
    Object projects = jmsTemplate.receiveAndConvert("PROJECT_EXPORT_QUEUE");
    System.out.println("-- All Projects");
    System.out.println(projects);
  }

  @Test
  void testReceiveMessage() {
   // MessageCreator mc = s -> s.createTextMessage("Hello Spring JMS!!! from creator");
    //jmsTemplate.send("test.send.receive", mc);
    jmsTemplate.convertAndSend("test.send.receive", "Hello Spring JMS!!! from creator");
  }

}
