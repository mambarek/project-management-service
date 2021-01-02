package com.it2go.micro.projectmanagement.services;

import com.it2go.micro.employeesservice.domian.Employee;
import java.util.List;
import java.util.UUID;

/**
 * created by mmbarek on 01.01.2021.
 */
public interface EmployeeService {

  List<Employee> findAllEmployees();
  Employee findEmployeeByPublicId(UUID publicId);
  Employee findEmployeeByPublicId(String publicId);
  Employee saveNewEmployee(Employee employee);
  Employee updateEmployee(Employee employee);
}
