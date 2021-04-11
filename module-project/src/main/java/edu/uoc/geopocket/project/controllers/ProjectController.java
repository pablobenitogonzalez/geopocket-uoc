package edu.uoc.geopocket.project.controllers;

import edu.uoc.geopocket.project.dtos.ProjectDTO;
import edu.uoc.geopocket.project.dtos.ProjectInputDTO;
import edu.uoc.geopocket.project.entities.Project;
import edu.uoc.geopocket.project.services.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*")
public class ProjectController {

    private ProjectService service;

    private ModelMapper modelMapper;

    @Autowired
    public ProjectController(final ProjectService service, final ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<ProjectDTO> findAll(final Pageable pageable) {
        final Page<Project> pageProject = service.findAll(pageable);
        return new PageImpl<>(Optional.of(pageProject.getContent())
            .orElse(Collections.emptyList()).stream().map(e -> modelMapper.map(e, ProjectDTO.class))
            .collect(Collectors.toList()), pageProject.getPageable(),
            pageProject.getTotalElements());
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProjectDTO> findAllByUser(final Pageable pageable) {
        final Page<Project> pageProject = service.findAllByUser(pageable);
        return new PageImpl<>(Optional.of(pageProject.getContent())
            .orElse(Collections.emptyList()).stream().map(e -> modelMapper.map(e, ProjectDTO.class))
            .collect(Collectors.toList()), pageProject.getPageable(),
            pageProject.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDTO findById(final @PathVariable("id") Long id) {
        return modelMapper.map(service.get(id), ProjectDTO.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProjectDTO create(final @RequestBody @Valid ProjectInputDTO dto) {
        return modelMapper.map(service.save(modelMapper.map(dto, Project.class)), ProjectDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDTO update(final @PathVariable("id") Long id, final @RequestBody @Valid ProjectInputDTO dto) {
        final Project project = modelMapper.map(dto, Project.class);
        project.setId(id);
        return modelMapper.map(service.save(project), ProjectDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(final @PathVariable("id") Long id) {
        service.delete(id);
    }
}
