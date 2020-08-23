package com.it2go.micro.projectmanagement.mapper;

import com.it2go.micro.projectmanagement.domain.Project;
import com.it2go.micro.projectmanagement.domain.ProjectStep;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectStepEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProjectMapper {

    Project projectEntityToProject(ProjectEntity projectEntity);

    ProjectEntity simpleProjectToProjectEntity(Project project);

    List<ProjectStep> projectEntitiesToProjectSteps(List<ProjectStepEntity> projectStepEntities);

    List<ProjectStepEntity> projectStepsToProjectEntities(List<ProjectStep> projectSteps);

    default ProjectEntity projectToProjectEntity(Project project) {
        ProjectEntity projectEntity = this.simpleProjectToProjectEntity(project);
        projectEntity.getProjectSteps().forEach(stepEntity -> stepEntity.setProject(projectEntity));

        return projectEntity;
    }

    // UPDATE existing entity
    // ignore steps collection this would be handled explicitly, because of id not existing in model
    @Mapping(source = "projectSteps", target = "projectSteps", ignore = true)
    ProjectEntity simpleUpdateProjectEntity(@MappingTarget ProjectEntity projectEntity,
        Project project);

    default ProjectEntity updateProjectEntity(@MappingTarget ProjectEntity projectEntity,
        Project project) {
        // update all direct attributes
        ProjectEntity updatedProjectEntity = this.simpleUpdateProjectEntity(projectEntity, project);
        // rescue ids
        Map<UUID, Long> stepsIdMap = new HashMap<>();

        // update the ids if exists
        projectEntity.getProjectSteps().forEach(projectStepEntity -> stepsIdMap
            .put(projectStepEntity.getPublicId(), projectStepEntity.getId()));

        List<ProjectStepEntity> projectStepEntities = projectStepsToProjectEntities(
            project.getProjectSteps());

        // now update reference
        for (ProjectStepEntity stepEntity : projectStepEntities) {
            if (stepsIdMap.containsKey(stepEntity.getPublicId())) {
                stepEntity.setId(stepsIdMap.get(stepEntity.getPublicId()));
            }
            stepEntity.setProject(updatedProjectEntity);
        }

        // reset projectStepEntities
        // IMPORTANT: do not replace the collection with new one. this would not work
        // while the JPA EntityManager a reference of this collection has

        //updatedProjectEntity.setProjectSteps(projectStepEntities);

        // the right way is to clear it and refill it again. this WORKS!!!
        updatedProjectEntity.getProjectSteps().clear();
        updatedProjectEntity.getProjectSteps().addAll(projectStepEntities);

        return updatedProjectEntity;
    }

}
