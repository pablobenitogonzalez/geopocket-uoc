package edu.uoc.geopocket.liquec.criterias;

import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.liquec.common.LiquecSearch;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.repositories.LiquecRepository;
import edu.uoc.geopocket.project.entities.Project;
import edu.uoc.geopocket.security.common.GeoPocketRole;
import edu.uoc.geopocket.security.helper.SecurityContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class LiquecCriteria {

    @PersistenceContext
    private EntityManager entityManager;

    private LiquecRepository liquecRepository;

    protected SecurityContextHelper securityContextHelper;

    @Autowired
    public LiquecCriteria(final LiquecRepository liquecRepository, final SecurityContextHelper securityContextHelper) {
        this.liquecRepository = liquecRepository;
        this.securityContextHelper = securityContextHelper;
    }

    public Page<Liquec> findAll(final LiquecSearch liquecSearch, final Pageable pageable) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Liquec> cq = cb.createQuery(Liquec.class);
        final Root<Liquec> liquecRoot = cq.from(Liquec.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(liquecSearch.getProjectId())) {
            final Join<Liquec, Project> projectJoin = liquecRoot.join("project");
            predicates.add(cb.equal(projectJoin.<Long>get("id"), liquecSearch.getProjectId()));
        }
        if (Objects.nonNull(liquecSearch.getStatus())) {
            predicates.add(cb.equal(liquecRoot.<String>get("status"), liquecSearch.getStatus()));
        }
        if (!securityContextHelper.hasRole(GeoPocketRole.ROLE_ADMIN)) {
            predicates.add(cb.equal(liquecRoot.<Audit>get("audit").<String>get("createdBy"), securityContextHelper.getUserName()));
        }

        final Predicate[] predArray = new Predicate[predicates.size()];
        predicates.toArray(predArray);
        cq.where(predArray);

        final List<Order> orders = new ArrayList<>();
        pageable.getSort().get().forEach( e -> {
            Order order;
            if (Sort.Direction.ASC.equals(e.getDirection())) {
                order = cb.asc(getPath(liquecRoot, e.getProperty()));
            } else {
                order = cb.desc(getPath(liquecRoot, e.getProperty()));
            }
            orders.add(order);
        });
        cq.orderBy(orders);

        final TypedQuery<Liquec> query = entityManager.createQuery(cq);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        Long count;
        if (securityContextHelper.hasRole(GeoPocketRole.ROLE_ADMIN)) {
            count = liquecRepository.count();
        } else {
            count = liquecRepository.countByAuditCreatedBy(securityContextHelper.getUserName());
        }
        return new PageImpl<>(query.getResultList(), pageable, count);
    }

    private Path<Liquec> getPath(Root<Liquec> root, String attributeName) {
        Path<Liquec> path = root;
        for (String part : attributeName.split("\\.")) {
            path = path.get(part);
        }
        return path;
    }


}
