package edu.uoc.geopocket.common.controllers;

import edu.uoc.geopocket.common.dtos.GeoPocketInputToolDTO;
import edu.uoc.geopocket.common.dtos.GeoPocketToolDTO;
import edu.uoc.geopocket.common.entities.GeoPocketToolEntity;
import edu.uoc.geopocket.common.entities.Search;
import edu.uoc.geopocket.common.mappers.AbstractInputMapper;
import edu.uoc.geopocket.common.mappers.AbstractToolMapper;
import edu.uoc.geopocket.common.services.ToolService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public abstract class AbstractToolController<E extends GeoPocketToolEntity,
        S extends Search, eDTO extends GeoPocketToolDTO, iDTO extends GeoPocketInputToolDTO, sDTO> {

    protected ToolService<E, S> service;

    protected AbstractToolMapper<E, eDTO> mapper;

    protected AbstractInputMapper<S, sDTO> searchMapper;

    protected AbstractInputMapper<E, iDTO> inputMapper;

    protected AbstractToolController(final ToolService<E, S> service,  AbstractToolMapper<E, eDTO> mapper,
                                     final AbstractInputMapper<S, sDTO> searchMapper,
                                     final AbstractInputMapper<E, iDTO> inputMapper) {
        this.service = service;
        this.mapper = mapper;
        this.searchMapper = searchMapper;
        this.inputMapper = inputMapper;
    }

    protected Page<eDTO> findAll(final sDTO searchDTO, final Pageable pageable) {
        final Page<E> pageLiquec = service.findAll(this.searchMapper.toEntity(searchDTO), pageable);
        return new PageImpl<>(mapper.toDTOs(pageLiquec.getContent()), pageLiquec.getPageable(),
                pageLiquec.getTotalElements());
    }

    protected eDTO findById(final Long id) {
        return mapper.toDTO(service.get(id));
    }

    protected eDTO saveDraft(final iDTO inputDTO) {
        return mapper.toDTO(service.saveDraft(inputMapper.toEntity(inputDTO), inputDTO.getProjectId()));
    }

    protected eDTO calculate(final iDTO inputDTO) {
        return mapper.toDTO(service.calculate(inputMapper.toEntity(inputDTO), inputDTO.getProjectId()));
    }

    protected void remove(final Long id) {
        service.delete(service.get(id));
    }

    protected eDTO clone(final Long id) {
        return mapper.toDTO(service.clone(id));
    }

}
