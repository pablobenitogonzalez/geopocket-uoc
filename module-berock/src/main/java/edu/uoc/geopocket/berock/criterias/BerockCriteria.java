package edu.uoc.geopocket.berock.criterias;

import edu.uoc.geopocket.berock.common.BerockSearch;
import edu.uoc.geopocket.berock.entities.Berock;
import edu.uoc.geopocket.berock.repositories.BerockRepository;
import edu.uoc.geopocket.common.entities.AbstractToolCriteria;
import edu.uoc.geopocket.security.helper.SecurityContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class BerockCriteria extends AbstractToolCriteria<Berock, Long, BerockSearch>  {

    @Autowired
    public BerockCriteria(final BerockRepository repository, final SecurityContextHelper securityContextHelper) {
        super(Berock.class, repository, securityContextHelper);
    }

    @Override
    protected void addCustomPredicates(BerockSearch search, CriteriaBuilder cb, Root<Berock> root, List<Predicate> predicates) {
        // Add more predicates
    }
}
