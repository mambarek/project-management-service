package com.it2go.micro.projectmanagement.persistence.jpa.entities;

import com.it2go.util.jpa.entities.AddressEntity;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class EmployeeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq_gen")
    @SequenceGenerator(name = "employee_seq_gen", sequenceName = "employee_seq", allocationSize = 50)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "PUBLIC_ID", unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @Basic
    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;

    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = 100)
    private String lastName;

    @Basic
    @Column(name = "BIRTH_DATE", columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(name = "GENDER", length = 10)
    private String gender;

    @Column(name = "EMAIL")
    private String email;

    @ManyToMany(mappedBy = "assignedEmployees")
    @ToString.Exclude
    private List<ProjectEntity> assignedProjects = new ArrayList<>();
}
