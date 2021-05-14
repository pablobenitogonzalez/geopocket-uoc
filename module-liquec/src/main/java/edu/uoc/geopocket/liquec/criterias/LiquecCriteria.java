package edu.uoc.geopocket.liquec.criterias;

import edu.uoc.geopocket.common.entities.AbstractToolCriteria;
import edu.uoc.geopocket.liquec.common.LiquecSearch;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.repositories.LiquecRepository;
import edu.uoc.geopocket.security.helper.SecurityContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class LiquecCriteria extends AbstractToolCriteria<Liquec, Long, LiquecSearch> {

    @Autowired
    public LiquecCriteria(final LiquecRepository repository, final SecurityContextHelper securityContextHelper) {
        super(Liquec.class, repository, securityContextHelper);
    }

    @Override
    protected void addCustomPredicates(LiquecSearch search, CriteriaBuilder cb, Root<Liquec> root, List<Predicate> predicates) {
        // Add more predicates
    }
}
