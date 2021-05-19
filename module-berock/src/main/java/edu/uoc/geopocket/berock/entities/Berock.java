package edu.uoc.geopocket.berock.entities;

import edu.uoc.geopocket.berock.common.RockType;
import edu.uoc.geopocket.berock.common.WeatheringDegree;
import edu.uoc.geopocket.berock.jpa.converters.RockTypeJpaConverter;
import edu.uoc.geopocket.berock.jpa.converters.WeatheringDegreeTypeJpaConverter;
import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.CalculationInfo;
import edu.uoc.geopocket.common.entities.GeoPocketToolEntity;
import edu.uoc.geopocket.common.jpa.converters.StatusJpaConverter;
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
@Table(name = "berock")
public class Berock implements GeoPocketToolEntity {

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

    @Convert(converter = RockTypeJpaConverter.class)
    @Column(name = "rock_type")
    private RockType rockType;

    @Convert(converter = WeatheringDegreeTypeJpaConverter.class)
    @Column(name = "weathering_degree")
    private WeatheringDegree weatheringDegree;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "berock_id")
    private List<Joint> joints;

    @Column(name = "rdq")
    private Float rockQualityDesignation;

    @Column(name = "uniaxial_compressive_strength")
    private Float uniaxialCompressiveStrength;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="berock_result_id")
    private BerockResult result;

    @Embedded
    private Audit audit = new Audit();

    @Embedded
    private CalculationInfo calculationInfo = new CalculationInfo();

}
