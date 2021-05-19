package edu.uoc.geopocket.liquec.entities;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import edu.uoc.geopocket.common.jpa.converters.BigDecimalConverter;
import edu.uoc.geopocket.common.jpa.converters.ResultJpaConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "liquec_spt_result")
public class SptResult implements GeoPocketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Convert(converter = ResultJpaConverter.class)
    @Column(name = "result")
    private Result result;

    @Column(name = "message")
    private String message;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "total_stress")
    private BigDecimal totalStress;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "interstitial_stress")
    private BigDecimal interstitialStress;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "effective_stress")
    private BigDecimal effectiveStress;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "effective_stress_factor")
    private BigDecimal effectiveStressFactor;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "earthquake_mag_corr")
    private BigDecimal earthquakeMagnitudeCorrection;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "coefficient_contrib_corr")
    private BigDecimal coefficientContributionCorrection;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "spt_corrected")
    private BigDecimal sptCorrected;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "cycle_stress_ratio")
    private BigDecimal cycleStressRatio;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "depth_factor")
    private BigDecimal depthFactor;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "cycle_resistance_ratio")
    private BigDecimal cycleResistanceRatio;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "cycle_resistance_ratio_corr")
    private BigDecimal cycleResistanceRatioCorrected;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "safety_factor")
    private BigDecimal safetyFactor;

    @Embedded
    private Audit audit = new Audit();

}
