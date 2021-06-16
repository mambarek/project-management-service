package com.it2go.micro.projectmanagement.services.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.domain.Employee;
import com.it2go.micro.employeesservice.domain.EmployeeExportEvent;
import com.it2go.micro.projectmanagement.config.MessagingConfig;
import com.it2go.micro.projectmanagement.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 01.01.2021.
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Profile("jms")
public class EmployeeEventListener {

  private final ObjectMapper objectMapper;
  private final EmployeeService employeeService;

  @JmsListener(destination = MessagingConfig.NEW_EMPLOYEES_QUEUE)
  public void listenToNewEmployee(Employee employee){
    log.info(String.format("-- listenToNewEmployee() %s", employee));
    employeeService.saveNewEmployee(employee);
  }

  @JmsListener(destination = MessagingConfig.UPDATED_EMPLOYEES_QUEUE)
  public void listenToUpdateEmployee(Employee employee){
    log.info(String.format("-- listenToUpdateEmployee() %s", employee));
    employeeService.updateEmployee(employee);
  }

  @JmsListener(destination = MessagingConfig.EMPLOYEES_EXPORT_QUEUE)
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
