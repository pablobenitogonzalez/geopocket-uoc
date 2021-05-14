package edu.uoc.geopocket.berock.dtos;

import edu.uoc.geopocket.common.dtos.GeoPocketInputToolDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class BerockInputDTO extends BerockBaseDTO implements GeoPocketInputToolDTO {
    private Long id;
    @NotNull
    private Long projectId;
    @Valid
    private List<JointInputDTO> joints;
}
