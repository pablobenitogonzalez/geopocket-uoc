package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.common.calculation.Describable;
import edu.uoc.geopocket.liquec.calculation.LiquecFines;
import lombok.Getter;

@Getter
public enum Crr implements Describable {
    LESS_OR_EQUAL("CRR <Less or equals>", new LiquecFines[] {LiquecFines.BOUND_05}),
    BETWEEN_FIRST_SECTION("CRR <Between fist section>", new LiquecFines[] {LiquecFines.BOUND_05, LiquecFines.BOUND_15}),
    BETWEEN_SECOND_SECTION("CRR <Between second section>", new LiquecFines[] {LiquecFines.BOUND_15, LiquecFines.BOUND_35}),
    GREATER_OR_EQUAL("CRR <Greater or equals>", new LiquecFines[] {LiquecFines.BOUND_35});

    private final String description;
    private final LiquecFines[] fines;

    Crr(final String description, final LiquecFines[] fines) {
        this.description = description;
        this.fines = fines;
    }

    public LiquecFines[] getBounds() {
        return fines;
    }
}
