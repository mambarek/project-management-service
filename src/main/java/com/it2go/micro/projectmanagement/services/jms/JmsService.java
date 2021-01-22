package com.it2go.micro.projectmanagement.services.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * created by mmbarek on 18.01.2021.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JmsService {

  private final JmsTemplate jmsTemplate;

  public void sendMessage(String queueName, Object body) throws SendMessageException {
    log.info("Send Message Queue: " + queueName + " Body: " + body);
    try {
      jmsTemplate.convertAndSend(queueName, body);
    }
    catch (Exception e){
      System.out.println(e.getMessage());
      throw new SendMessageException("Error sending message!");
    }
  }

  public Object receiveMessage(String queueName) throws ReceiveMessageException {
    log.info("Receive Message Queue: " + queueName);
    try {
      return jmsTemplate.receiveAndConvert(queueName);
    }catch (Exception e){
      System.out.println(e.getMessage());
      throw new ReceiveMessageException("Error occurred receiving message!");
    }
  }
}
