package edu.uoc.geopocket.liquec.calculation;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;

public enum Polynomial {

    // EurocodeTask - EvaluableFines 05
    EUROCODE_F05_LESS_OR_EQUAL(
            BigDecimal.valueOf(+0000.0480000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),
    EUROCODE_F05_BETWEEN_FIST_SECTION(
            BigDecimal.valueOf(+0000.0448296329),
            BigDecimal.valueOf(-0000.0094070222),
            BigDecimal.valueOf(+0000.0033050307),
            BigDecimal.valueOf(-0000.0002385061), 
            BigDecimal.valueOf(+0000.0000079813),
            BigDecimal.valueOf(-0000.0000000987),
            BigDecimal.valueOf(+0000.0000000000)),
    EUROCODE_F05_BETWEEN_SECOND_SECTION(
            BigDecimal.valueOf(-8043.1432771184),
            BigDecimal.valueOf(+1489.9589620681),
            BigDecimal.valueOf(-0110.3230416596),
            BigDecimal.valueOf(+0004.0816050267),
            BigDecimal.valueOf(-0000.0754533111),
            BigDecimal.valueOf(+0000.0005575932),
            BigDecimal.valueOf(+0000.0000000000)),
    EUROCODE_F05_GREATER_OR_EQUAL(
            BigDecimal.valueOf(+0000.6000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),

    // EurocodeTask - EvaluableFines 15
    EUROCODE_F15_LESS_OR_EQUAL(
            BigDecimal.valueOf(+0000.0960000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),
    EUROCODE_F15_BETWEEN_FIST_SECTION(
            BigDecimal.valueOf(-0000.1343385091),
            BigDecimal.valueOf(+0000.0974834289),
            BigDecimal.valueOf(-0000.0162815857),
            BigDecimal.valueOf(+0000.0014800712),
            BigDecimal.valueOf(-0000.0000645096),
            BigDecimal.valueOf(+0000.0000010911),
            BigDecimal.valueOf(+0000.0000000000)),
    EUROCODE_F15_BETWEEN_SECOND_SECTION(
            BigDecimal.valueOf(+0350.7533268770),
            BigDecimal.valueOf(-0065.7224752419),
            BigDecimal.valueOf(+0004.6129687346),
            BigDecimal.valueOf(-0000.1436660680),
            BigDecimal.valueOf(+0000.0016758777),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),
    EUROCODE_F15_GREATER_OR_EQUAL(
            BigDecimal.valueOf(+0000.6000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),

    // EurocodeTask - EvaluableFines 35
    EUROCODE_F35_LESS_OR_EQUAL(
            BigDecimal.valueOf(+0000.0960000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),
    EUROCODE_F35_BETWEEN_FIST_SECTION(
            BigDecimal.valueOf(+0000.0562712584),
            BigDecimal.valueOf(+0000.0235304846),
            BigDecimal.valueOf(-0000.0031087842),
            BigDecimal.valueOf(+0000.0003716392),
            BigDecimal.valueOf(-0000.0000196952),
            BigDecimal.valueOf(+0000.0000003475),
            BigDecimal.valueOf(+0000.0000000029)),
    EUROCODE_F35_BETWEEN_SECOND_SECTION(
            BigDecimal.valueOf(+0699.5094771763),
            BigDecimal.valueOf(-0154.6774955751),
            BigDecimal.valueOf(+0012.8221180239),
            BigDecimal.valueOf(-0000.4721399728),
            BigDecimal.valueOf(+0000.0065173735),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),
    EUROCODE_F35_GREATER_OR_EQUAL(
            BigDecimal.valueOf(+0000.6000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),

    // NCSE-02 - EvaluableFines 05
    NCSE02_F05_LESS_OR_EQUAL(
            BigDecimal.valueOf(+0000.0600000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),
    NCSE02_F05_BETWEEN(
            BigDecimal.valueOf(+0000.6808078098),
            BigDecimal.valueOf(-0000.3256813155),
            BigDecimal.valueOf(+0000.0642887905),
            BigDecimal.valueOf(-0000.0061087131),
            BigDecimal.valueOf(+0000.0003071981),
            BigDecimal.valueOf(-0000.0000078144),
            BigDecimal.valueOf(+0000.0000000792)),
    NCSE02_F05_GREATER_OR_EQUAL(
            BigDecimal.valueOf(+0000.6000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),

    // NCSE-02 - EvaluableFines 15
    NCSE02_F15_LESS_OR_EQUAL(
            BigDecimal.valueOf(+0000.0966000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),
    NCSE02_F15_BETWEEN(
            BigDecimal.valueOf(+0000.7423315466),
            BigDecimal.valueOf(-0000.3968346345),
            BigDecimal.valueOf(+0000.0923208225),
            BigDecimal.valueOf(-0000.0104384920),
            BigDecimal.valueOf(+0000.0006297183),
            BigDecimal.valueOf(-0000.0000193490),
            BigDecimal.valueOf(+0000.0000002383)),
    NCSE02_F15_GREATER_OR_EQUAL(
            BigDecimal.valueOf(+0000.6000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),

    // NCSE-02 - EvaluableFines 35
    NCSE02_F35_LESS_OR_EQUAL(
            BigDecimal.valueOf(+0000.1050000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),
    NCSE02_F35_BETWEEN(
            BigDecimal.valueOf(+0000.3795425287),
            BigDecimal.valueOf(-0000.2556070600),
            BigDecimal.valueOf(+0000.0865399943),
            BigDecimal.valueOf(-0000.0134605429),
            BigDecimal.valueOf(+0000.0010825978),
            BigDecimal.valueOf(-0000.0000433242),
            BigDecimal.valueOf(+0000.0000006829)),
    NCSE02_F35_GREATER_OR_EQUAL(
            BigDecimal.valueOf(+0000.6000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000),
            BigDecimal.valueOf(+0000.0000000000)),

    // Earthquake Magnitude
    EARTHQUAKE_MAGNITUDE_CORRECTION(
            BigDecimal.valueOf(+0298.8700000000),
            BigDecimal.valueOf(-0219.4910000000),
            BigDecimal.valueOf(+0066.3450000000),
            BigDecimal.valueOf(-0010.1500000000),
            BigDecimal.valueOf(+0000.7800000000),
            BigDecimal.valueOf(-0000.0240000000),
            BigDecimal.valueOf(+0000.0000000000));

    private BigDecimal position0;
    private BigDecimal position1;
    private BigDecimal position2;
    private BigDecimal position3;
    private BigDecimal position4;
    private BigDecimal position5;
    private BigDecimal position6;
    protected static final MathContext MATH_CONTEXT = new MathContext(15);

    Polynomial(final BigDecimal position0, final BigDecimal position1, final BigDecimal position2,
               final BigDecimal position3, final BigDecimal position4, final BigDecimal position5,
               final BigDecimal position6) {
        this.position0 = position0;
        this.position1 = position1;
        this.position2 = position2;
        this.position3 = position3;
        this.position4 = position4;
        this.position5 = position5;
        this.position6 = position6;
    }

    public BigDecimal getPosition0() {
        return position0;
    }

    public BigDecimal getPosition1() {
        return position1;
    }

    public BigDecimal getPosition2() {
        return position2;
    }

    public BigDecimal getPosition3() {
        return position3;
    }

    public BigDecimal getPosition4() {
        return position4;
    }

    public BigDecimal getPosition5() {
        return position5;
    }

    public BigDecimal getPosition6() {
        return position6;
    }

    public BigDecimal getValue(final BigDecimal argument) {
        BigDecimal value = BigDecimal.valueOf(0.0);
        for (int i = 0; i <= 6; i++) {
            value = value.add(this.getConstant(i).multiply(BigDecimalMath.pow(argument, i, MATH_CONTEXT)));
        }
        return value;
    }

    private BigDecimal getConstant(final int position) {
        switch (position) {
            case 0: return this.getPosition0();
            case 1: return this.getPosition1();
            case 2: return this.getPosition2();
            case 3: return this.getPosition3();
            case 4: return this.getPosition4();
            case 5: return this.getPosition5();
            case 6: return this.getPosition6();
            default: return BigDecimal.valueOf(0);
        }
    }

}
