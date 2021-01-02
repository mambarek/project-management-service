package com.it2go.micro.projectmanagement.web.controller;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.projectmanagement.services.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by mmbarek on 02.01.2021.
 */
@RestController
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;

  @GetMapping("/api/v1/employees")
  public List<Employee> findAllEmployees(){
    return employeeService.findAllEmployees();
  }
}
