package edu.uoc.geopocket.common.controllers;

import edu.uoc.geopocket.common.dtos.GeoPocketInputDTO;
import edu.uoc.geopocket.common.dtos.GeoPocketProjectDTO;
import edu.uoc.geopocket.common.dtos.GeoPocketRecordingDTO;
import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.common.services.AbstractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractController<
        RecordingDTO extends GeoPocketRecordingDTO,
        InputDTO extends GeoPocketInputDTO,
        ProjectDTO extends GeoPocketProjectDTO,
        E extends GeoPocketEntity, ID extends Serializable> {

    @Autowired
    protected ModelMapper modelMapper;

    private Class<E> eClass;
    private Class<ProjectDTO> dtoClass;

    protected abstract AbstractService<E> getService();

    protected E convertToEntity(final RecordingDTO dto) {
        if (dto == null) return null;
        return modelMapper.map(dto,eClass);
    }

    protected E convertToEntity(final InputDTO dto) {
        if (dto == null) return null;
        return modelMapper.map(dto,eClass);
    }

    protected ProjectDTO convertToDTO(final E e) {
        if (e == null) return null;
        return modelMapper.map(e, dtoClass);
    }

    protected AbstractController(final Class<E> eClass, final Class<ProjectDTO> dtoClass) {
        this.eClass = eClass;
        this.dtoClass = dtoClass;
    }

    public ResponseEntity<ProjectDTO> findById(final @PathVariable("id") ID id) {
        return new ResponseEntity<>(convertToDTO(getService().get((Long) id)), HttpStatus.OK);
    }

    public void remove(final @PathVariable("id") ID id) {
        getService().delete(getService().get((Long) id));
    }

    protected List<ProjectDTO> convertToDTOList(final List<E> list) {
        return Optional.ofNullable(list)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    protected void checkIds(ID id, InputDTO dto) {
        if (!id.equals(dto.getId())) {
            throw new GeoPocketException("Both ids must be the same");
        }
    }

}
