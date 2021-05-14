package edu.uoc.geopocket.berock.controllers;

import edu.uoc.geopocket.berock.common.BerockSearch;
import edu.uoc.geopocket.berock.dtos.BerockDTO;
import edu.uoc.geopocket.berock.dtos.BerockInputDTO;
import edu.uoc.geopocket.berock.dtos.BerockSearchDTO;
import edu.uoc.geopocket.berock.entities.Berock;
import edu.uoc.geopocket.berock.mappers.BerockInputMapper;
import edu.uoc.geopocket.berock.mappers.BerockMapper;
import edu.uoc.geopocket.berock.mappers.BerockSearchMapper;
import edu.uoc.geopocket.berock.services.BerockServiceImpl;
import edu.uoc.geopocket.common.controllers.AbstractToolController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/berock")
public class BerockController extends AbstractToolController<Berock, BerockSearch, BerockDTO, BerockInputDTO, BerockSearchDTO> {

    @Autowired
    public BerockController(final BerockServiceImpl service,
                            final BerockMapper mapper, final BerockSearchMapper searchMapper,
                            final BerockInputMapper inputMapper) {
        super(service, mapper, searchMapper, inputMapper);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<BerockDTO> findAll(final BerockSearchDTO searchDTO, final Pageable pageable) {
        return super.findAll(searchDTO, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BerockDTO findById(final @PathVariable("id") Long id) {
        return super.findById(id);
    }

    @PostMapping("/draft")
    @ResponseStatus(HttpStatus.OK)
    public BerockDTO saveDraft(final @RequestBody BerockInputDTO inputDTO) {
        return super.saveDraft(inputDTO);
    }

    @PostMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public BerockDTO calculate(final @RequestBody @Valid BerockInputDTO inputDTO) {
        return super.calculate(inputDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(final @PathVariable("id") Long id) {
        super.remove(id);
    }

    @PostMapping("/{id}/clone")
    @ResponseStatus(HttpStatus.OK)
    public BerockDTO clone(final @PathVariable("id") Long id) {
        return super.clone(id);
    }

}
