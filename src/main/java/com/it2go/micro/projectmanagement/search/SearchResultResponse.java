package com.it2go.micro.projectmanagement.search;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultResponse {

  private List<ProjectTableItem> projectTableItems;

}
