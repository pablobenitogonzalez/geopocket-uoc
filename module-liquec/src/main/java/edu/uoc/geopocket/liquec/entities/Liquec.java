package edu.uoc.geopocket.liquec.entities;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.CalculationInfo;
import edu.uoc.geopocket.common.entities.GeoPocketToolEntity;
import edu.uoc.geopocket.common.jpa.converters.StatusJpaConverter;
import edu.uoc.geopocket.liquec.common.LiquecCode;
import edu.uoc.geopocket.liquec.jpa.converters.CodeJpaConverter;
import edu.uoc.geopocket.project.entities.Project;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "liquec")
public class Liquec implements GeoPocketToolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Convert(converter = StatusJpaConverter.class)
    @Column(name = "status")
    private Status status;

    @Convert(converter = CodeJpaConverter.class)
    @Column(name = "code")
    private LiquecCode code;

    @Column(name = "peak_ground_acc")
    private Float peakGroundAcceleration;

    @Column(name = "earthquake_mag")
    private Float earthquakeMagnitude;

    @Column(name = "coefficient_of_contrib")
    private Float coefficientOfContribution;

    @Column(name = "gwt_depth")
    private Float groundWaterTableDepth;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "liquec_id")
    private List<SoilLayer> soilLayers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "liquec_id")
    private List<Spt> spts;

    @Embedded
    private Audit audit = new Audit();

    @Embedded
    private CalculationInfo calculationInfo = new CalculationInfo();

}
