package com.it2go.micro.projectmanagement.mapper;

import com.it2go.micro.projectmanagement.domain.ProjectStep;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectStepEntity;
import org.mapstruct.MappingTarget;

public interface ProjectStepMapper {

    ProjectStep projectStepEntityToProjectStep(ProjectStep projectStep);

    ProjectStepEntity projectStepEntityToProjectStep(ProjectStepEntity projectStepEntity);

    ProjectStepEntity updateProjectStepEntityToProjectStep(
        @MappingTarget ProjectStepEntity projectStepEntity, ProjectStep projectStep);
}
