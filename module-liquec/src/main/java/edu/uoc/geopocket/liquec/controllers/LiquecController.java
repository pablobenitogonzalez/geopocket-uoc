package edu.uoc.geopocket.liquec.controllers;

import edu.uoc.geopocket.common.controllers.AbstractToolController;
import edu.uoc.geopocket.liquec.common.LiquecSearch;
import edu.uoc.geopocket.liquec.dtos.LiquecDTO;
import edu.uoc.geopocket.liquec.dtos.LiquecInputDTO;
import edu.uoc.geopocket.liquec.dtos.LiquecSearchDTO;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.mappers.LiquecInputMapper;
import edu.uoc.geopocket.liquec.mappers.LiquecMapper;
import edu.uoc.geopocket.liquec.mappers.LiquecSearchMapper;
import edu.uoc.geopocket.liquec.services.LiquecServiceImpl;
import edu.uoc.geopocket.liquec.validation.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/liquec")
public class LiquecController extends AbstractToolController<Liquec, LiquecSearch, LiquecDTO, LiquecInputDTO, LiquecSearchDTO> {

    private final ValidationHelper validationHelper;

    @Autowired
    public LiquecController(final LiquecServiceImpl service, final ValidationHelper validationHelper,
                            final LiquecMapper mapper, final LiquecSearchMapper searchMapper,
                            final LiquecInputMapper inputMapper) {
        super(service, mapper, searchMapper, inputMapper);
        this.validationHelper = validationHelper;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<LiquecDTO> findAll(final LiquecSearchDTO searchDTO, final Pageable pageable) {
        return super.findAll(searchDTO, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LiquecDTO findById(final @PathVariable("id") Long id) {
        return super.findById(id);
    }

    @PostMapping("/draft")
    @ResponseStatus(HttpStatus.OK)
    public LiquecDTO saveDraft(final @RequestBody LiquecInputDTO inputDTO) {
        validationHelper.validateDraft(inputDTO);
        return super.saveDraft(inputDTO);
    }

    @PostMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public LiquecDTO calculate(final @RequestBody @Valid LiquecInputDTO inputDTO) {
        validationHelper.validateSoilLayers(inputDTO.getSoilLayers());
        validationHelper.validateSpts(inputDTO.getSoilLayers(), inputDTO.getSpts());
        return super.calculate(inputDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(final @PathVariable("id") Long id) {
        super.remove(id);
    }

    @PostMapping("/{id}/clone")
    @ResponseStatus(HttpStatus.OK)
    public LiquecDTO clone(final @PathVariable("id") Long id) {
        return super.clone(id);
    }

}
