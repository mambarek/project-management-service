package com.it2go.micro.projectmanagement.services.amqp;

import com.it2go.micro.projectmanagement.services.messagin.MessageService;
import com.it2go.micro.projectmanagement.services.messagin.ReceiveMessageException;
import com.it2go.micro.projectmanagement.services.messagin.SendMessageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 24.01.2021.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("rabbit")
public class AmqpService implements MessageService {

  private final RabbitTemplate rabbitTemplate;

  @Override
  public void sendMessage(String queueName, Object body) throws SendMessageException {
    log.info("-- amqp send message!");
    try {
      rabbitTemplate.convertAndSend(queueName, body);
    }
    catch (Exception e){
      System.out.println(e.getMessage());
      throw new SendMessageException("Error sending message!", e);
    }
  }

  @Override
  public Object receiveAndConvertMessage(String queueName) throws ReceiveMessageException {
    log.info("-- amqp receiveAndConvertMessage!");
    try {
      return rabbitTemplate.receiveAndConvert(queueName);
    }catch (Exception e){
      System.out.println(e.getMessage());
      throw new ReceiveMessageException("Error occurred receiving message!", e);
    }
  }
}
