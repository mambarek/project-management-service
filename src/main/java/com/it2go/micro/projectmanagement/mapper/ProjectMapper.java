package com.it2go.micro.projectmanagement.mapper;

import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.domain.ProjectStep;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectStepEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface ProjectMapper {

    Project projectEntityToProject(ProjectEntity projectEntity);

    ProjectEntity simpleProjectToProjectEntity(Project project);

    default ProjectEntity projectToProjectEntity(Project project) {
        ProjectEntity projectEntity = this.simpleProjectToProjectEntity(project);
        projectEntity.getProjectSteps().forEach(stepEntity -> stepEntity.setProject(projectEntity));

        return projectEntity;
    }

    // UPDATE existing entity
    ProjectEntity simpleUpdateProjectEntity(@MappingTarget ProjectEntity projectEntity,
        Project project);

    default ProjectEntity updateProjectEntity(@MappingTarget ProjectEntity projectEntity,
        Project project) {
        // update all direct attributes
        ProjectEntity updatedProjectEntity = this.simpleUpdateProjectEntity(projectEntity, project);
        // now update reference
        updatedProjectEntity.getProjectSteps()
            .forEach(stepEntity -> stepEntity.setProject(updatedProjectEntity));

        return updatedProjectEntity;
    }

    // implement explicitly an update for the list this would be
    // checked and called automatically from MapStruct in simpleUpdateProjectEntity
    List<ProjectStepEntity> updateProjectStepEntities(
        @MappingTarget List<ProjectStepEntity> projectStepEntities, List<ProjectStep> projectSteps);
}
