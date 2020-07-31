package com.it2go.micro.projectmanagement.persistence.jpa.repositories;

import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

  Optional<ProjectEntity> findByPublicId(UUID publicId);
}
