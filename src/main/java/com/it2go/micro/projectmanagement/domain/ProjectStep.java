package com.it2go.micro.projectmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectStep {

    @NotNull
    private UUID publicId;
    private String name;
    private String description;
    private Double budget;
    private LocalDate planedStartDate;
    private LocalDate planedFinishDate;
    private LocalDate startDate;
    private LocalDate finishDate;
    private ProjectStepStatus status;
}
