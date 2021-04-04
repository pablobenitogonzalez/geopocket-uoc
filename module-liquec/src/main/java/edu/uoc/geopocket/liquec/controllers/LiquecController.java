package edu.uoc.geopocket.liquec.controllers;

import edu.uoc.geopocket.common.controllers.AbstractToolController;
import edu.uoc.geopocket.liquec.dtos.LiquecDTO;
import edu.uoc.geopocket.liquec.dtos.LiquecInputDTO;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.services.LiquecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/liquec")
public class LiquecController extends AbstractToolController<Liquec, LiquecDTO, LiquecInputDTO> {

    private LiquecService service;

    @Autowired
    public LiquecController(final LiquecService service) {
        super(Liquec.class, LiquecDTO.class);
        this.service = service;
    }

    @Override
    protected LiquecService getService() {
        return this.service;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<LiquecDTO> findAll(final Pageable pageable) {
        final Page<Liquec> pageLiquec = service.findAll(pageable);
        return new PageImpl<>(convertToDTOList(pageLiquec.getContent()), pageLiquec.getPageable(),
            pageLiquec.getTotalElements());
    }

    // TODO: search filter
    @GetMapping("/project/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<LiquecDTO> findAllByProject(final @PathVariable("id") Long id, final Pageable pageable) {
        final Page<Liquec> pageLiquec = service.findAllByProject(id, pageable);
        return new PageImpl<>(convertToDTOList(pageLiquec.getContent()), pageLiquec.getPageable(),
            pageLiquec.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LiquecDTO findById(final @PathVariable("id") Long id) {
        return super.findById(id);
    }

    @PostMapping("/draft")
    @ResponseStatus(HttpStatus.OK)
    public LiquecDTO saveDraft(final @RequestBody @Valid LiquecInputDTO dto) {
        return convertToDTO(getService().saveDraft(convertToEntity(dto), dto.getProjectId()));
    }

    @PostMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public LiquecDTO calculate(final @RequestBody @Valid LiquecInputDTO dto) {
        return convertToDTO(getService().calculate(convertToEntity(dto), dto.getProjectId()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(final @PathVariable("id") Long id) {
        super.remove(id);
    }

}
