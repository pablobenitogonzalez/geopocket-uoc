package edu.uoc.geopocket.common.controllers;

import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import edu.uoc.geopocket.common.services.AbstractToolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractToolController<E extends GeoPocketEntity, D, I> {

    @Autowired
    protected ModelMapper modelMapper;

    private Class<E> eClass;
    private Class<D> dtoClass;

    protected abstract AbstractToolService<E> getService();

    protected E convertToEntity(final I inputDto) {
        if (inputDto == null) return null;
        return modelMapper.map(inputDto, eClass);
    }

    protected D convertToDTO(final E e) {
        if (e == null) return null;
        return modelMapper.map(e, dtoClass);
    }

    protected AbstractToolController(final Class<E> eClass, final Class<D> dtoClass) {
        this.eClass = eClass;
        this.dtoClass = dtoClass;
    }

    protected D findById(final Long id) {
        return convertToDTO(getService().get(id));
    }

    protected void remove(final Long id) {
        getService().delete(getService().get(id));
    }

    protected List<D> convertToDTOList(final List<E> list) {
        return Optional.ofNullable(list).orElse(Collections.emptyList()).stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
