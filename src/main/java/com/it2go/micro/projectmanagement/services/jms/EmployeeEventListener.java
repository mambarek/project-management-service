package com.it2go.micro.projectmanagement.services.jms;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.projectmanagement.mapper.EmployeeMapper;
import com.it2go.micro.projectmanagement.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 01.01.2021.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EmployeeEventListener {

  private final EmployeeService employeeService;

  @JmsListener(destination = "NEW_EMPLOYEE_QUEUE")
  public void listenToNewEmployee(Employee employee){
    log.info(String.format("-- listenToNewEmployee() %s", employee));
    employeeService.saveNewEmployee(employee);
  }

  @JmsListener(destination = "EMPLOYEE_CHANGED_QUEUE")
  public void listenToUpdateEmployee(Employee employee){
    log.info(String.format("-- listenToUpdateEmployee() %s", employee));
    employeeService.updateEmployee(employee);
  }
}
