package com.it2go.micro.projectmanagement.persistence.jpa.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROJECT")
public class ProjectEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq_gen")
  @SequenceGenerator(name = "project_seq_gen", sequenceName = "project_seq", allocationSize = 50)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

    @Column(name = "PUBLIC_ID", unique = true, nullable = false)
    private UUID publicId;

    @Basic
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 500)
    private String description;

    @Basic
    @Column(name = "BUDGET", precision = 2)
    private Double budget;

    @Basic
    @Column(name = "PS_DATE", columnDefinition = "DATE")
    private LocalDate planedStartDate;

    @Basic
    @Column(name = "PF_DATE", columnDefinition = "DATE")
    private LocalDate planedFinishDate;

    @Basic
    @Column(name = "S_DATE", columnDefinition = "DATE")
    private LocalDate startDate;

    @Basic
    @Column(name = "F_DATE", columnDefinition = "DATE")
    private LocalDate finishDate;

    @Basic
    @Column(name = "STATUS", length = 20)
    private String status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProjectStepEntity> projectSteps;

    public void addProjectStep(ProjectStepEntity stepEntity) {
        stepEntity.setProject(this);
        // attention lazy loading
        if (this.getProjectSteps() == null) {
            this.projectSteps = new ArrayList<>();
        }
        this.projectSteps.add(stepEntity);
    }
}
