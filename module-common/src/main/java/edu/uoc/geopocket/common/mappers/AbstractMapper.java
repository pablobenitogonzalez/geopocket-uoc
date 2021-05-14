package edu.uoc.geopocket.common.mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractMapper<E, DTO> extends AbstractInputMapper<E, DTO> {

    public abstract DTO toDTO(E e);

    public List<DTO> toDTOs(final List<E> list) {
        return Optional.ofNullable(list).orElse(Collections.emptyList()).stream().map(this::toDTO).collect(Collectors.toList());
    }

}
