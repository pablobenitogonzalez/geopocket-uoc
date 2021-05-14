package edu.uoc.geopocket.berock.entities;

import edu.uoc.geopocket.berock.common.RockType;
import edu.uoc.geopocket.berock.common.WeatheringDegree;
import edu.uoc.geopocket.berock.jpa.converters.RockTypeJpaConverter;
import edu.uoc.geopocket.berock.jpa.converters.WeatheringDegreeTypeJpaConverter;
import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.CalculationInfo;
import edu.uoc.geopocket.common.entities.ColumnDefinitions;
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
@Table(name = "BEROCK")
public class Berock implements GeoPocketToolEntity {

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

    @Convert(converter = RockTypeJpaConverter.class)
    @Column(name = "ROCK_TYPE", columnDefinition = ColumnDefinitions.VARCHAR_30)
    private RockType rockType;

    @Convert(converter = WeatheringDegreeTypeJpaConverter.class)
    @Column(name = "WEATHERING_DEGREE", columnDefinition = ColumnDefinitions.VARCHAR_30)
    private WeatheringDegree weatheringDegree;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "BEROCK_ID")
    private List<Joint> joints;

    @Column(name = "RDQ")
    private Float rockQualityDesignation;

    @Column(name = "UNIAXIAL_COMPRESSIVE_STRENGTH")
    private Float uniaxialCompressiveStrength;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="BEROCK_RESULT_ID")
    private BerockResult result;

    @Embedded
    private Audit audit = new Audit();

    @Embedded
    private CalculationInfo calculationInfo = new CalculationInfo();

}
