package com.it2go.micro.projectmanagement.services.jms;

import com.it2go.micro.projectmanagement.services.messagin.MessageService;
import com.it2go.micro.projectmanagement.services.messagin.ReceiveMessageException;
import com.it2go.micro.projectmanagement.services.messagin.SendMessageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 18.01.2021.
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Profile("jms")
public class JmsService implements MessageService {

  private final JmsTemplate jmsTemplate;

  @Override
  public void sendMessage(String queueName, Object body) throws SendMessageException {
    log.info("-- JMS Send Message Queue: " + queueName + " Body: " + body);
    try {
      jmsTemplate.convertAndSend(queueName, body);
    }
    catch (Exception e){
      System.out.println(e.getMessage());
      throw new SendMessageException("Error sending message!");
    }
  }

  @Override
  public Object receiveAndConvertMessage(String queueName) throws ReceiveMessageException {
    log.info("-- JMS receiveAndConvertMessage Queue: " + queueName);
    try {
      return jmsTemplate.receiveAndConvert(queueName);
    }catch (Exception e){
      System.out.println(e.getMessage());
      throw new ReceiveMessageException("Error occurred receiving message!");
    }
  }
}
