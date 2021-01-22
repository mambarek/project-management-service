package com.it2go.micro.projectmanagement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Slf4j
@Configuration
@EnableJms
public class JmsConfig {

  public static final String PROJECT_EVENTS_QUEUE = "project_events";
  public static final String NEW_PROJECTS_QUEUE = "NEW_PROJECTS_QUEUE";
  public static final String PROJECTS_CHANGED_QUEUE = "PROJECTS_CHANGED_QUEUE";

  @Bean // Serialize message content to json using TextMessage
  public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
    System.out.println("-- jacksonJmsMessageConverter called");
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    //converter.setTargetType(MessageType.TEXT); // the json payload
    converter.setTargetType(MessageType.BYTES); // the json payload
    converter.setTypeIdPropertyName("_type"); // the type full class name
    converter.setObjectMapper(objectMapper);

    //now set idMappings for serialization/deserialization
    //HashMap<String, Class<?>> idMapping = new HashMap<>();
    //idMapping.put(Project.class.getName(), Project.class);
    //idMapping.put(String.class.getName(), String.class);
    //idMapping.put(ArrayList.cl)

    //converter.setTypeIdMappings(idMapping);

    return converter;
  }

}
