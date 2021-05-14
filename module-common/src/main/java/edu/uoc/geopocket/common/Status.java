package edu.uoc.geopocket.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum Status {
    DRAFT (0, "draft"),
    CALCULATED(1, "calculated");

    private Integer id;
    private String name;

    Status(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonValue
    public Integer getId() {
        return id;
    }

    @JsonCreator
    public static Status create(String id) {
        try {
            return Status.getStatus(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Status getStatus(final Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return Arrays.stream(Status.values()).filter(result -> id.equals(result.getId())).findFirst().orElse(null);
    }
}
