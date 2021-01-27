package com.it2go.micro.projectmanagement.services.amqp;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.projectmanagement.config.MessagingConfig;
import com.it2go.micro.projectmanagement.services.messagin.SendMessageException;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * created by mmbarek on 25.01.2021.
 */
@SpringBootTest
@ActiveProfiles("rabbit")
class AmqpServiceTest {

  @Autowired
  AmqpService amqpService;

  @Test
  void testSendReceive() throws Exception {
    amqpService.sendMessage(MessagingConfig.EMPLOYEES_IMPORT_QUEUE,"");
/*    Object message = amqpService.receiveMessage(MessagingConfig.EMPLOYEES_EXPORT_QUEUE);
    System.out.println("message: " + message);
    String m = new String(((Message) message).getBody(), "UTF-8");
    System.out.println("MESSAGE: " + m);*/
    Object message2 = amqpService.receiveAndConvertMessage(MessagingConfig.EMPLOYEES_EXPORT_QUEUE);
    System.out.println("message2: " + message2);
  }
}
