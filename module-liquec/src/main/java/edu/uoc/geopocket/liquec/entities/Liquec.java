package edu.uoc.geopocket.liquec.entities;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.entities.*;
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
@Table(name = "LIQUEC")
public class Liquec implements GeoPocketToolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @Convert(converter = StatusJpaConverter.class)
    @Column(name = "STATUS", columnDefinition = ColumnDefinitions.VARCHAR_30)
    private Status status;

    @Convert(converter = CodeJpaConverter.class)
    @Column(name = "CODE", columnDefinition = ColumnDefinitions.VARCHAR_30)
    private LiquecCode code;

    @Column(name = "PEAK_GROUND_ACC")
    private Float peakGroundAcceleration;

    @Column(name = "EARTHQUAKE_MAG")
    private Float earthquakeMagnitude;

    @Column(name = "COEFFICIENT_OF_CONTRIB")
    private Float coefficientOfContribution;

    @Column(name = "GWT_DEPTH")
    private Float groundWaterTableDepth;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LIQUEC_ID")
    private List<SoilLayer> soilLayers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LIQUEC_ID")
    private List<Spt> spts;

    @Embedded
    private Audit audit = new Audit();

    @Embedded
    private CalculationInfo calculationInfo = new CalculationInfo();

}
