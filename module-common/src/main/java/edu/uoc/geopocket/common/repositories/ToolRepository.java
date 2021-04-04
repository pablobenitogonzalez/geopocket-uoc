package edu.uoc.geopocket.common.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToolRepository<T> {
    Page<T> findAllByProject(Long projectId, Pageable page);
}
