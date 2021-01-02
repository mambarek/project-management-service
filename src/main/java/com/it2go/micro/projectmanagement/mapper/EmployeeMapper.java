package com.it2go.micro.projectmanagement.mapper;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.EmployeeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * created by mmbarek on 01.01.2021.
 */
@Mapper
public interface EmployeeMapper {

  @Mapping(source = "firstName", target = "data.firstName")
  @Mapping(source = "lastName", target = "data.lastName")
  @Mapping(source = "birthDate", target = "data.birthDate")
  @Mapping(source = "gender", target = "data.gender")
  @Mapping(source = "email", target = "data.email")
  Employee employeeEntityToEmployee(EmployeeEntity employeeEntity);

  @InheritInverseConfiguration(name = "employeeEntityToEmployee")
  EmployeeEntity employeeToEmployEntity(Employee employee);

}
