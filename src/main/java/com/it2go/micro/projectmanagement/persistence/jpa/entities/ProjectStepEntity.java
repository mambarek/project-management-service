package com.it2go.micro.projectmanagement.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROJECT_STEP")
public class ProjectStepEntity implements
    Serializable { // serializable is needed from JPA, without it rise exception

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "PUBLIC_ID", unique = true, nullable = false)
    private UUID publicId;

    @Basic
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Basic
    @Column(name = "DESC", nullable = false, length = 500)
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

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PUBLIC_ID", nullable = false)
    @JsonIgnore
    private ProjectEntity project;

    @Basic
    @Column(name = "STATUS", length = 20)
    private String status;
}
