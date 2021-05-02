package edu.uoc.geopocket.liquec.services;

import edu.uoc.geopocket.liquec.common.LiquecSearch;
import edu.uoc.geopocket.liquec.entities.Liquec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LiquecService {
    Page<Liquec> findAll(final LiquecSearch liquecSearch, final Pageable pageable);
}
