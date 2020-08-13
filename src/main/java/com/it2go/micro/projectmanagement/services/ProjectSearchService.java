package com.it2go.micro.projectmanagement.services;

import com.it2go.micro.projectmanagement.search.ProjectTableItem;
import com.it2go.util.jpa.search.SearchTemplate;
import java.util.List;

public interface ProjectSearchService {

  public List<ProjectTableItem> filterProjects(SearchTemplate searchTemplate);
}
