package com.it2go.micro.projectmanagement.events;

import com.it2go.micro.projectmanagement.domain.Project;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by mmbarek on 29.12.2020.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectExportEvent implements Serializable {

    static final long serialVersionUID = -7455219239661735842L;

    private List<Project> projects;
}
