package edu.uoc.geopocket.liquec.entities;

import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "liquec_soil_layer")
public class SoilLayer implements GeoPocketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "start_depth")
    private Float startDepth;

    @Column(name = "final_depth")
    private Float finalDepth;

    @Column(name = "soil_type")
    private String soilType;

    @Column(name = "above_gwt")
    private Float aboveGwt;

    @Column(name = "below_gwt")
    private Float belowGwt;

    @Column(name = "fines_content")
    private Float finesContent;

    @Column(name = "check_liquefaction")
    private Boolean checkLiquefaction;

    @Embedded
    private Audit audit = new Audit();

}
