package com.it2go.micro.projectmanagement.services.impl;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.projectmanagement.mapper.EmployeeMapper;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.projectmanagement.persistence.jpa.repositories.EmployeeRepository;
import com.it2go.micro.projectmanagement.services.EmployeeService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * created by mmbarek on 01.01.2021.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;

  @Override
  public List<Employee> findAllEmployees() {
    List<Employee> result = new ArrayList<>();
    employeeRepository.findAll().forEach(employeeEntity -> {
      result.add(employeeMapper.employeeEntityToEmployee(employeeEntity));
    });

    return result;
  }

  @Override
  public Employee findEmployeeByPublicId(UUID publicId) {
    EmployeeEntity employeeEntity = employeeRepository.findByPublicId(publicId).orElse(null);
    if(employeeEntity == null){
      return null;
    }
    return employeeMapper.employeeEntityToEmployee(employeeEntity);
  }

  @Override
  public Employee findEmployeeByPublicId(String publicId) {
    return findEmployeeByPublicId(UUID.fromString(publicId));
  }

  @Override
  public Employee saveNewEmployee(Employee employee) {
    EmployeeEntity employeeEntity = employeeMapper.employeeToEmployEntity(employee);
    EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);

    return employeeMapper.employeeEntityToEmployee(savedEntity);
  }

  @Override
  public Employee updateEmployee(Employee employee) {
    EmployeeEntity employeeEntity = employeeMapper.employeeToEmployEntity(employee);

    EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);

    return employeeMapper.employeeEntityToEmployee(savedEntity);
  }
}
