package edu.uoc.geopocket.common.services;

import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractToolService<T extends GeoPocketEntity> {

    public abstract JpaRepository<T, Long> getRepository();

    public T get(final long id) {
        return this.getRepository().findById(id).orElseThrow(() -> new GeoPocketException(String.format("Entity %s not found", id)));
    }

    public abstract T saveDraft(final T element, final Long projectId);

    public abstract T calculate(final T element, final Long projectId);

    public void delete(final T element) {
        this.getRepository().delete(element);
    }

    public abstract T clone(final Long id);

}
