package com.it2go.micro.projectmanagement.services;

import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.persistence.jpa.entities.ProjectEntity_;
import com.it2go.micro.projectmanagement.search.ProjectTableItem;
import com.it2go.util.jpa.search.PredicateBuilder;
import com.it2go.util.jpa.search.SearchTemplate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectSearchServiceImpl implements ProjectSearchService {

  final EntityManager entityManager;

  @Override
  public List<ProjectTableItem> filterProjects(SearchTemplate searchTemplate) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<ProjectTableItem> criteriaQuery = cb.createQuery(ProjectTableItem.class);
    Root<ProjectEntity> projectEntityRoot = criteriaQuery.from(ProjectEntity.class);

    final CompoundSelection<ProjectTableItem> compoundSelection = cb
        .construct(ProjectTableItem.class,
            projectEntityRoot.get(ProjectEntity_.publicId),
            projectEntityRoot.get(ProjectEntity_.name),
            projectEntityRoot.get(ProjectEntity_.description),
            projectEntityRoot.get(ProjectEntity_.budget),
            projectEntityRoot.get(ProjectEntity_.planedStartDate),
            projectEntityRoot.get(ProjectEntity_.planedFinishDate),
            projectEntityRoot.get(ProjectEntity_.startDate),
            projectEntityRoot.get(ProjectEntity_.finishDate),
            projectEntityRoot.get(ProjectEntity_.status));

    final CriteriaQuery<ProjectTableItem> select = criteriaQuery.select(compoundSelection);

    PredicateBuilder predicateBuilder = null;
    if (searchTemplate.getFilters() != null) {
      predicateBuilder = PredicateBuilder
          .createPredicates(cb, projectEntityRoot, searchTemplate.getFilters());

      select.where(predicateBuilder.getPredicates().toArray(new Predicate[0]));
    }

    // Order by
    Order orderBy = null;

    if (searchTemplate.getOrderBy() != null && !searchTemplate.getOrderBy()
        .isEmpty()) {
      switch (searchTemplate.getOrderDirection()) {
        case "asc":
          orderBy = cb.asc(projectEntityRoot.get(searchTemplate.getOrderBy()));
          break;
        case "desc":
          orderBy = cb.desc(projectEntityRoot.get(searchTemplate.getOrderBy()));
          break;
      }
    }

    if (orderBy != null) {
      select.orderBy(orderBy);
    }

    final TypedQuery<ProjectTableItem> query = entityManager.createQuery(select);

    // set query parameter if exists
    if (predicateBuilder != null) {
      predicateBuilder.getParamMap().forEach(query::setParameter);
    }

    if (searchTemplate.getMaxResult() > 0) {
      query.setMaxResults(searchTemplate.getMaxResult());
    }

    query.setFirstResult(searchTemplate.getOffset());

    return query.getResultList();
  }
}
