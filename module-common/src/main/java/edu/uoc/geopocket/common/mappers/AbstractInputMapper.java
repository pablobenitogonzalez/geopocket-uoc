package edu.uoc.geopocket.common.mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractInputMapper<E, DTO> {

    public abstract E toEntity(DTO e);

    public List<E> toEntities(final List<DTO> list) {
        return Optional.ofNullable(list).orElse(Collections.emptyList()).stream().map(this::toEntity).collect(Collectors.toList());
    }

}
