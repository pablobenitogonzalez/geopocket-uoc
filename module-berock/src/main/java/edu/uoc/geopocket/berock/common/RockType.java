package edu.uoc.geopocket.berock.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@Getter
public enum RockType {
    GROUP_1(1, "Group 1 (carbonate rock, well structured)", BigDecimal.valueOf(1.0)),
    GROUP_2(2, "Group 2 (igneous and metamorphic rocks)", BigDecimal.valueOf(0.8)),
    GROUP_3(3, "Group 3 (sedimentary and some metamorphic rocks)", BigDecimal.valueOf(0.6)),
    GROUP_4(4, "Group 4 (other rocks)", BigDecimal.valueOf(0.4));

    private Integer id;
    private String name;
    private BigDecimal alpha;

    RockType(final Integer id, final String name, final BigDecimal alpha) {
        this.id = id;
        this.name = name;
        this.alpha = alpha;
    }

    @JsonValue
    public Integer getId() {
        return id;
    }

    @JsonCreator
    public static RockType create(String id) {
        try {
            return RockType.getRockType(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static RockType getRockType(final Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return Arrays.stream(RockType.values()).filter(rockType -> id.equals(rockType.getId())).findFirst().orElse(null);
    }
}
