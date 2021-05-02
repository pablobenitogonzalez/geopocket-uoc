package edu.uoc.geopocket.liquec.controllers;

import edu.uoc.geopocket.common.controllers.AbstractToolController;
import edu.uoc.geopocket.liquec.common.LiquecSearch;
import edu.uoc.geopocket.liquec.dtos.LiquecDTO;
import edu.uoc.geopocket.liquec.dtos.LiquecInputDTO;
import edu.uoc.geopocket.liquec.dtos.LiquecSearchDTO;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.services.LiquecServiceImpl;
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

    private LiquecServiceImpl service;

    @Autowired
    public LiquecController(final LiquecServiceImpl service) {
        super(Liquec.class, LiquecDTO.class);
        this.service = service;
    }

    @Override
    protected LiquecServiceImpl getService() {
        return this.service;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<LiquecDTO> findAll(final LiquecSearchDTO liquecSearchDTO, final Pageable pageable) {
        final Page<Liquec> pageLiquec = service.findAll(this.modelMapper.map(liquecSearchDTO, LiquecSearch.class), pageable);
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
    public LiquecDTO saveDraft(final @RequestBody LiquecInputDTO dto) {
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

    @PostMapping("/{id}/clone")
    @ResponseStatus(HttpStatus.OK)
    public LiquecDTO calculate(final @PathVariable("id") Long id) {
        return convertToDTO(getService().clone(id));
    }

}
