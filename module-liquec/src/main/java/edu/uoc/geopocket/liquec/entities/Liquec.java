package edu.uoc.geopocket.liquec.entities;

import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.ColumnDefinitions;
import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import edu.uoc.geopocket.liquec.common.LiquecCode;
import edu.uoc.geopocket.liquec.common.LiquecStatus;
import edu.uoc.geopocket.project.entities.Project;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "LIQUEC")
public class Liquec implements GeoPocketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", columnDefinition = ColumnDefinitions.VARCHAR2_30)
    private LiquecStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "CODE", columnDefinition = ColumnDefinitions.VARCHAR2_30)
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

}
