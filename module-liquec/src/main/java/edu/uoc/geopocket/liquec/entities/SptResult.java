package edu.uoc.geopocket.liquec.entities;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import edu.uoc.geopocket.common.jpa.converters.ResultJpaConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "LIQUEC_SPT_RESULT")
public class SptResult implements GeoPocketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Convert(converter = ResultJpaConverter.class)
    @Column(name = "RESULT")
    private Result result;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "TOTAL_STRESS")
    private Double totalStress;

    @Column(name = "INTERSTITIAL_STRESS")
    private Double interstitialStress;

    @Column(name = "EFFECTIVE_STRESS")
    private Double effectiveStress;

    @Column(name = "EFFECTIVE_STRESS_FACTOR")
    private Double effectiveStressFactor;

    @Column(name = "EARTHQUAKE_MAG_CORR")
    private Double earthquakeMagnitudeCorrection;

    @Column(name = "COEFFICIENT_CONTRIB_CORR")
    private Double coefficientContributionCorrection;

    @Column(name = "SPT_CORRECTED")
    private Double sptCorrected;

    @Column(name = "CYCLE_STRESS_RATIO")
    private Double cycleStressRatio;

    @Column(name = "DEPTH_FACTOR")
    private Double depthFactor;

    @Column(name = "CYCLE_RESISTANCE_RATIO")
    private Double cycleResistanceRatio;

    @Column(name = "CYCLE_RESISTANCE_RATIO_CORR")
    private Double cycleResistanceRatioCorrected;

    @Column(name = "SAFETY_FACTOR")
    private Double safetyFactor;

    @Embedded
    private Audit audit = new Audit();

}
