package edu.uoc.geopocket.liquec.validation;

import edu.uoc.geopocket.common.exceptions.GeoPocketValidationException;
import edu.uoc.geopocket.liquec.dtos.LiquecInputDTO;
import edu.uoc.geopocket.liquec.dtos.SoilLayerInputDTO;
import edu.uoc.geopocket.liquec.dtos.SptInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ValidationHelper {

    private Validator validator;

    @Autowired
    public ValidationHelper(Validator validator) {
        this.validator = validator;
    }

    public void validateDraft(final LiquecInputDTO liquecInputDTO) {
        Optional.ofNullable(liquecInputDTO.getSoilLayers()).orElse(Collections.emptyList()).forEach(layer -> {
            final Set<ConstraintViolation<SoilLayerInputDTO>> constraintViolations = validator.validate(layer);
            if (!constraintViolations.isEmpty()) {
                throw new GeoPocketValidationException("There are constraint validations in soil layers", constraintViolations);
            }
        });
        Optional.ofNullable(liquecInputDTO.getSpts()).orElse(Collections.emptyList()).forEach(spt -> {
            final Set<ConstraintViolation<SptInputDTO>> constraintViolations = validator.validate(spt);
            if (!constraintViolations.isEmpty()) {
                throw new GeoPocketValidationException("There are constraint validations in soil layers", constraintViolations);
            }
        });
        validateSoilLayers(liquecInputDTO.getSoilLayers());
        validateSpts(liquecInputDTO.getSoilLayers(), liquecInputDTO.getSpts());
    }

    public void validateSoilLayers(final List<SoilLayerInputDTO> soilLayers) {
        final List<SoilLayerInputDTO> sortedLayers = Optional.ofNullable(soilLayers).orElse(Collections.emptyList()).stream()
                .sorted(Comparator.comparing(SoilLayerInputDTO::getStartDepth)).collect(Collectors.toList());
        if (sortedLayers.isEmpty()) {
            return;
        }
        if (sortedLayers.get(0).getStartDepth() != 0.0) {
            throw new GeoPocketValidationException("First depth soil layer must start at 0.0", Collections.emptySet());
        }
        for(int i = 0; i < sortedLayers.size() - 1; i++) {
            if (!sortedLayers.get(i).getFinalDepth().equals(sortedLayers.get(i+1).getStartDepth())) {
                throw new GeoPocketValidationException("Soil layers must be correlative", Collections.emptySet());
            }
        }
    }

    public void validateSpts(final List<SoilLayerInputDTO> soilLayers, final List<SptInputDTO> spts) {
        final List<SoilLayerInputDTO> sortedLayers = Optional.ofNullable(soilLayers).orElse(Collections.emptyList()).stream()
                .sorted(Comparator.comparing(SoilLayerInputDTO::getStartDepth)).collect(Collectors.toList());
        final List<SptInputDTO> sortedSpts = Optional.ofNullable(spts).orElse(Collections.emptyList()).stream()
                .sorted(Comparator.comparing(SptInputDTO::getDepth)).collect(Collectors.toList());
        if (sortedLayers.isEmpty() && sortedSpts.size() > 0) {
            throw new GeoPocketValidationException("There can be no spts without soil layers", Collections.emptySet());
        }
        final Float maxDepth = sortedLayers.get(sortedLayers.size()-1).getFinalDepth();
        sortedSpts.forEach(spt -> {
            if (spt.getDepth() > maxDepth) {
                throw new GeoPocketValidationException("An spt can't be deeper than the bottom soil layer", Collections.emptySet());
            }
        });
    }
}
