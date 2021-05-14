package edu.uoc.geopocket.common.services;

import edu.uoc.geopocket.common.entities.Search;
import edu.uoc.geopocket.common.entities.GeoPocketToolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToolService<T extends GeoPocketToolEntity, S extends Search> {
    Page<T> findAll(final S search, final Pageable pageable);
    T get(final long id);
    T saveDraft(final T entity, final Long projectId);
    T calculate(final T entity, final Long projectId);
    void delete(final T element);
    T clone(final Long id);
}
