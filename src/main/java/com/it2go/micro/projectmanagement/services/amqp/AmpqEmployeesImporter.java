package com.it2go.micro.projectmanagement.services.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.domian.EmployeeExportEvent;
import com.it2go.micro.projectmanagement.config.MessagingConfig;
import com.it2go.micro.projectmanagement.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 25.01.2021.
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Profile("rabbit")
public class AmpqEmployeesImporter {

  private final ObjectMapper objectMapper;
  private final EmployeeService employeeService;

  @RabbitListener(queues = MessagingConfig.EMPLOYEES_EXPORT_QUEUE)
  public void importEmployees(String employeeExportEventJson){
    System.out.println("-- importEmployees Called imported was:  " + employeeExportEventJson);
    if(employeeExportEventJson == null) return;

    System.out.println("-- Employee import save all employees!");
    try {
      EmployeeExportEvent employeeExportEvent = objectMapper
          .readValue(employeeExportEventJson, EmployeeExportEvent.class);
      employeeExportEvent.getEmployees().forEach(employeeService::saveNewEmployee);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
