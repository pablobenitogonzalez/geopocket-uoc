package edu.uoc.geopocket.liquec.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum LiquecCode {
    EUROCODE(0, "Eurocode", 1.25f),
    NCSE_02(1, "NCSE-02", 1.50f);

    private Integer id;
    private String name;
    private Float safetyFactor;

    LiquecCode(final Integer id, final String name, final Float safetyFactor) {
        this.id = id;
        this.name = name;
        this.safetyFactor = safetyFactor;
    }

    @JsonValue
    public Integer getId() {
        return id;
    }

    @JsonCreator
    public static LiquecCode create(String id) {
        try {
            return LiquecCode.getLiquecCode(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static LiquecCode getLiquecCode(final Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return Arrays.stream(LiquecCode.values()).filter(liquecCode -> id.equals(liquecCode.getId())).findFirst().orElse(null);
    }
}
