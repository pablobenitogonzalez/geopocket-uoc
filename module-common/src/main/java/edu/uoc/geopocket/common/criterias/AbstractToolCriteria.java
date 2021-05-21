package edu.uoc.geopocket.common.criterias;

import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.GeoPocketProjectEntity;
import edu.uoc.geopocket.common.entities.GeoPocketToolEntity;
import edu.uoc.geopocket.common.entities.Search;
import edu.uoc.geopocket.common.repositories.ToolRepository;
import edu.uoc.geopocket.security.common.GeoPocketRole;
import edu.uoc.geopocket.security.helper.SecurityContextHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractToolCriteria<T extends GeoPocketToolEntity, ID, S extends Search> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected ToolRepository<T, ID> repository;

    protected SecurityContextHelper securityContextHelper;

    protected Class<T> clazz;

    public AbstractToolCriteria(final Class<T> clazz, final ToolRepository<T, ID> repository,
                                final SecurityContextHelper securityContextHelper) {
        this.clazz = clazz;
        this.repository = repository;
        this.securityContextHelper = securityContextHelper;
    }

    public Page<T> findAll(final S search, final Pageable pageable) {
        // result list
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(clazz);
        final Root<T> root = cq.from(clazz);
        final List<Predicate> resultListPredicates = addPredicates(search, cb, root);
        addCustomPredicates(search, cb, root, resultListPredicates);
        cq.where(predicatesToArray(resultListPredicates));
        final List<Order> orders = new ArrayList<>();
        pageable.getSort().get().forEach( e -> {
            Order order;
            if (Sort.Direction.ASC.equals(e.getDirection())) {
                order = cb.asc(getPath(root, e.getProperty()));
            } else {
                order = cb.desc(getPath(root, e.getProperty()));
            }
            orders.add(order);
        });
        cq.orderBy(orders);
        final TypedQuery<T> query = entityManager.createQuery(cq);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        final List<T> result = query.getResultList();

        // single result
        final CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
        final Root<T> rootCount = cqCount.from(clazz);
        cqCount.select(cb.count(rootCount));
        final List<Predicate> singleResultPredicates = addPredicates(search, cb, rootCount);
        addCustomPredicates(search, cb, rootCount, singleResultPredicates);
        cqCount.where(predicatesToArray(singleResultPredicates));
        final Long count = entityManager.createQuery(cqCount).getSingleResult();

        return new PageImpl<>(result, pageable, count);
    }

    private List<Predicate> addPredicates(final S search, final CriteriaBuilder cb, final Root<T> root) {
        final List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(search.getProjectId())) {
            final Join<T, GeoPocketProjectEntity> projectJoin = root.join("project");
            predicates.add(cb.equal(projectJoin.<Long>get("id"), search.getProjectId()));
        }
        if (Objects.nonNull(search.getStatus())) {
            predicates.add(cb.equal(root.<Integer>get("status"), search.getStatus().getId()));
        }
        if (!securityContextHelper.hasRole(GeoPocketRole.ROLE_PROFESSOR)) {
            predicates.add(cb.equal(root.<Audit>get("audit").<String>get("createdBy"), securityContextHelper.getUserName()));
        }
        return predicates;
    }

    private Predicate[] predicatesToArray(final List<Predicate> predicates) {
        final Predicate[] predArray = new Predicate[predicates.size()];
        return predicates.toArray(predArray);
    }

    protected abstract void addCustomPredicates(S search, CriteriaBuilder cb, Root<T> root, List<Predicate> predicates);

    protected Path<T> getPath(Root<T> root, String attributeName) {
        Path<T> path = root;
        for (String part : attributeName.split("\\.")) {
            path = path.get(part);
        }
        return path;
    }


}
