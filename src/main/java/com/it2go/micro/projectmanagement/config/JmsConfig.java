package com.it2go.micro.projectmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

  public static final String PROJECT_EVENTS_QUEUE = "project-events";
  public static final String NEW_PROJECTS_QUEUE = "NEW_PROJECTS_QUEUE";
  public static final String PROJECTS_CHANGED_QUEUE = "PROJECTS_CHANGED_QUEUE";

  //@Bean // Serialize message content to json using TextMessage
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT); // the json payload
    converter.setTypeIdPropertyName("_type"); // the type full class name

    return converter;
  }


}
