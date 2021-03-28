package edu.uoc.geopocket.common.services;

import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractService<T extends GeoPocketEntity> {

    public abstract JpaRepository<T, Long> getRepository();

    public List<T> findAll() {
        return this.getRepository().findAll();
    }

    public T get(final long id) {
        return this.getRepository().findById(id).orElse(null);
    }

    public abstract T record(final T element);

    public abstract T saveDraft(final T element);

    public abstract T calculate(final T element);

    public void delete(final T element) {
        this.getRepository().delete(element);
    }

}
