package edu.uoc.geopocket.project.controllers;

import edu.uoc.geopocket.project.dtos.ProjectDTO;
import edu.uoc.geopocket.project.dtos.ProjectInputDTO;
import edu.uoc.geopocket.project.entities.Project;
import edu.uoc.geopocket.project.mappers.ProjectInputMapper;
import edu.uoc.geopocket.project.mappers.ProjectMapper;
import edu.uoc.geopocket.project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService service;

    private final ProjectMapper mapper;

    private final ProjectInputMapper inputMapper;

    @Autowired
    public ProjectController(final ProjectService service, final ProjectMapper mapper,
                             final ProjectInputMapper inputMapper) {
        this.service = service;
        this.mapper = mapper;
        this.inputMapper = inputMapper;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<ProjectDTO> findAll(final Pageable pageable) {
        final Page<Project> pageProject = service.findAll(pageable);
        return new PageImpl<>(Optional.of(pageProject.getContent())
            .orElse(Collections.emptyList()).stream().map(mapper::toDTO)
            .collect(Collectors.toList()), pageProject.getPageable(),
            pageProject.getTotalElements());
    }

    @GetMapping("/autocomplete")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDTO> autocomplete(final @Param("name") String name) {
        final List<Project> projects = service.autocomplete(name);
        return Optional.of(projects)
                .orElse(Collections.emptyList()).stream().map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDTO findById(final @PathVariable("id") Long id) {
        return mapper.toDTO(service.get(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProjectDTO create(final @RequestBody @Valid ProjectInputDTO dto) {
        return mapper.toDTO(service.save(inputMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDTO update(final @PathVariable("id") Long id, final @RequestBody @Valid ProjectInputDTO dto) {
        final Project project = inputMapper.toEntity(dto);
        project.setId(id);
        return mapper.toDTO(service.save(project));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(final @PathVariable("id") Long id) {
        service.delete(id);
    }

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public Long countProjects() {
        return service.countProjects();
    }
}
