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
@Table(name = "LIQUEC_SOIL_LAYER")
public class SoilLayer implements GeoPocketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "START_DEPTH")
    private Float startDepth;

    @Column(name = "FINAL_DEPTH")
    private Float finalDepth;

    @Column(name = "SOIL_TYPE")
    private String soilType;

    @Column(name = "ABOVE_GWT")
    private Float aboveGwt;

    @Column(name = "BELOW_GWT")
    private Float belowGwt;

    @Column(name = "FINES_CONTENT")
    private Float finesContent;

    @Column(name = "CHECK_LIQUEFACTION")
    private Boolean checkLiquefaction;

    @Embedded
    private Audit audit = new Audit();

}
