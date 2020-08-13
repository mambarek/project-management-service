package com.it2go.micro.projectmanagement.search;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class ProjectTableItem implements Serializable {

  private UUID publicId;
  private String name;
  private String description;
  private Double budget;
  private LocalDate planedStartDate;
  private LocalDate planedFinishDate;
  private LocalDate startDate;
  private LocalDate finishDate;
  private String status;
}
