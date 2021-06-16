package com.it2go.micro.projectmanagement.mapper;

import com.it2go.micro.employeesservice.domain.Employee;
import com.it2go.micro.employeesservice.domain.Gender;
import com.it2go.micro.employeesservice.domain.PersonData;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * created by mmbarek on 01.01.2021.
 */
@Mapper
public interface EmployeeMapper {

  default Employee employeeEntityToEmployee(EmployeeEntity employeeEntity){
    if ( employeeEntity == null ) {
      return null;
    }

    Employee employee = new Employee();
    PersonData personData = new PersonData();

    personData.setFirstName(employeeEntity.getFirstName());
    personData.setLastName(employeeEntity.getLastName());
    personData.setBirthDate(employeeEntity.getBirthDate());
    personData.setGender(Gender.valueOf(employeeEntity.getGender()));
    personData.setEmail(employeeEntity.getEmail());

    employee.setData(personData);
    employee.publicId( employeeEntity.getPublicId() );

    return employee;
  }

  @Mapping(source = "data.firstName", target = "firstName")
  @Mapping(source = "data.lastName", target = "lastName")
  @Mapping(source = "data.birthDate", target = "birthDate")
  @Mapping(source = "data.gender", target = "gender")
  @Mapping(source = "data.email", target = "email")
  @Mapping(source = "assignedProjects", target = "assignedProjects", ignore = true)
  EmployeeEntity employeeToEmployEntity(Employee employee);

}
