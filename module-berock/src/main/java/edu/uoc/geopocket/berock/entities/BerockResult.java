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
@Table(name = "berock_result")
public class BerockResult implements GeoPocketEntity {

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
    @Column(name = "allowable_bearing_pressure")
    private BigDecimal allowableBearingPressure;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "alpha_3a")
    private BigDecimal alpha3a;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "alpha_3b")
    private BigDecimal alpha3b;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "final_alpha_3")
    private BigDecimal finalAlpha3;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "jv")
    private BigDecimal volumetricJointCount;

    @Convert(converter = BigDecimalConverter.class)
    @Column(name = "calculated_rdq")
    private BigDecimal calculatedRDQ;

    @Embedded
    private Audit audit = new Audit();

}

