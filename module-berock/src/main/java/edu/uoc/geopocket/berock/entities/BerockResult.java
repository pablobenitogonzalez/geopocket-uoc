package edu.uoc.geopocket.berock.entities;

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
@Table(name = "BEROCK_RESULT")
public class BerockResult implements GeoPocketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Convert(converter = ResultJpaConverter.class)
    @Column(name = "RESULT")
    private Result result;

    @Column(name = "MESSAGE")
    private String message;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "ALLOWABLE_BEARING_PRESSURE")
    private BigDecimal allowableBearingPressure;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "ALPHA_3A")
    private BigDecimal alpha3a;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "ALPHA_3B")
    private BigDecimal alpha3b;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "FINAL_ALPHA_3")
    private BigDecimal finalAlpha3;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "JV")
    private BigDecimal volumetricJointCount;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "CALCULATED_RDQ")
    private BigDecimal calculatedRDQ;

    @Embedded
    private Audit audit = new Audit();

}

