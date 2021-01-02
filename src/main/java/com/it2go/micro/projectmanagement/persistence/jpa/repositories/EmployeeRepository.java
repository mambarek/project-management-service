package com.it2go.micro.projectmanagement.persistence.jpa.repositories;

import com.it2go.micro.projectmanagement.persistence.jpa.entities.EmployeeEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

/**
 * created by mmbarek on 01.01.2021.
 */
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {
  Optional<EmployeeEntity> findByPublicId(UUID publicId);
}
