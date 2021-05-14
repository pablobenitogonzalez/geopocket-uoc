package edu.uoc.geopocket.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum Result {
    OK(0, "ok"),
    SKIP(1, "skip"),
    ERROR(-1, "error");

    private Integer id;
    private String name;

    Result(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonValue
    public Integer getId() {
        return id;
    }

    @JsonCreator
    public static Result create(String id) {
        try {
            return Result.getResult(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Result getResult(final Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return Arrays.stream(Result.values()).filter(result -> id.equals(result.getId())).findFirst().orElse(null);
    }
}
