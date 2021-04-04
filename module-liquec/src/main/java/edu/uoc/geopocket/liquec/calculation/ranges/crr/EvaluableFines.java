package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.liquec.calculation.Polynomial;
import lombok.Getter;

@Getter
public enum EvaluableFines {
    EUROCODE_F05_LESS_OR_EQUAL("EUROCODE fines 05 <Less or equal>", Polynomial.EUROCODE_F05_LESS_OR_EQUAL),
    EUROCODE_F05_BETWEEN_FIRST_SECTION("EUROCODE fines 05 <Between first section>", Polynomial.EUROCODE_F05_BETWEEN_FIST_SECTION),
    EUROCODE_F05_BETWEEN_SECOND_SECTION("EUROCODE fines 05 <Between second section>", Polynomial.EUROCODE_F05_BETWEEN_SECOND_SECTION),
    EUROCODE_F05_GREATER_OR_EQUAL("EUROCODE fines 05 <Greater or equal>", Polynomial.EUROCODE_F05_GREATER_OR_EQUAL),
    EUROCODE_F15_LESS_OR_EQUAL("EUROCODE fines 15 <Less or equal>", Polynomial.EUROCODE_F15_LESS_OR_EQUAL),
    EUROCODE_F15_BETWEEN_FIRST_SECTION("EUROCODE fines 15 <Between first section>", Polynomial.EUROCODE_F15_BETWEEN_FIST_SECTION),
    EUROCODE_F15_BETWEEN_SECOND_SECTION("EUROCODE fines 15 <Between second section>", Polynomial.EUROCODE_F15_BETWEEN_SECOND_SECTION),
    EUROCODE_F15_GREATER_OR_EQUAL("EUROCODE fines 15 <Greater or equal>", Polynomial.EUROCODE_F15_GREATER_OR_EQUAL),
    EUROCODE_F35_LESS_OR_EQUAL("EUROCODE fines 35 <Less or equal>", Polynomial.EUROCODE_F35_LESS_OR_EQUAL),
    EUROCODE_F35_BETWEEN_FIRST_SECTION("EUROCODE fines 35 <Between first section>", Polynomial.EUROCODE_F35_BETWEEN_FIST_SECTION),
    EUROCODE_F35_BETWEEN_SECOND_SECTION("EUROCODE fines 35 <Between second section>", Polynomial.EUROCODE_F35_BETWEEN_SECOND_SECTION),
    EUROCODE_F35_GREATER_OR_EQUAL("EUROCODE fines 35 <Greater or equal>", Polynomial.EUROCODE_F35_GREATER_OR_EQUAL),
    NCSE02_F05_LESS_OR_EQUAL("NCSE-02 fines 05 <Less or equal", Polynomial.NCSE02_F05_LESS_OR_EQUAL),
    NCSE02_F05_BETWEEN("NCSE-02 fines 05 <Between>", Polynomial.NCSE02_F05_BETWEEN),
    NCSE02_F05_GREATER_OR_EQUAL("NCSE-02 fines 05 <Greater or equal>", Polynomial.NCSE02_F05_GREATER_OR_EQUAL),
    NCSE02_F15_LESS_OR_EQUAL("NCSE-02 fines 15 <Less or equal>", Polynomial.NCSE02_F15_LESS_OR_EQUAL),
    NCSE02_F15_BETWEEN("NCSE-02 fines 15 <Between>", Polynomial.NCSE02_F15_BETWEEN),
    NCSE02_F15_GREATER_OR_EQUAL("NCSE-02 fines 15 <Greater or equal>", Polynomial.NCSE02_F15_GREATER_OR_EQUAL),
    NCSE02_F35_LESS_OR_EQUAL("NCSE-02 fines 35 <Less or equal>", Polynomial.NCSE02_F35_LESS_OR_EQUAL),
    NCSE02_F35_BETWEEN("NCSE-02 fines 35 <Between>", Polynomial.NCSE02_F35_BETWEEN),
    NCSE02_F35_GREATER_OR_EQUAL("NCSE-02 fines 35 <Greater or equal>", Polynomial.NCSE02_F35_GREATER_OR_EQUAL);

    private String description;
    private Polynomial polynomial;

    EvaluableFines(final String description, final Polynomial polynomial) {
        this.description = description;
        this.polynomial = polynomial;
    }
}
