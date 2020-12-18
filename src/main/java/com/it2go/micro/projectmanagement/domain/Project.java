package com.it2go.micro.projectmanagement.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project implements Serializable {

  static final long serialVersionUID = 645618260758668328L;

  private UUID publicId;
  private String name;
  private String description;
  private Double budget;
  private LocalDate planedStartDate;
  private LocalDate planedFinishDate;
  private LocalDate startDate;
  private LocalDate finishDate;
  private ProjectStatus status;

  private List<ProjectStep> projectSteps;
}
