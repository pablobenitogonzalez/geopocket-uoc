package edu.uoc.geopocket.berock.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@Getter
public enum WeatheringDegree {
    DEGREE_1(1, "Degree 1 (unweathered rock)", BigDecimal.valueOf(1.0)),
    DEGREE_2(2, "Degree 2 (slightly weathered rock)", BigDecimal.valueOf(0.7)),
    DEGREE_3(3, "Degree 3 (moderately weathered rock)", BigDecimal.valueOf(0.5));

    private Integer id;
    private String name;
    private BigDecimal alpha;

    WeatheringDegree(final Integer id, final String name, final BigDecimal alpha) {
        this.id = id;
        this.name = name;
        this.alpha = alpha;
    }

    @JsonValue
    public Integer getId() {
        return id;
    }

    @JsonCreator
    public static WeatheringDegree create(String id) {
        try {
            return WeatheringDegree.getWeatheringDegree(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static WeatheringDegree getWeatheringDegree(final Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return Arrays.stream(WeatheringDegree.values()).filter(weatheringDegree -> id.equals(weatheringDegree.getId())).findFirst().orElse(null);
    }
}
