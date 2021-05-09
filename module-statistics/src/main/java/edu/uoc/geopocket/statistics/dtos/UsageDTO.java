package edu.uoc.geopocket.statistics.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsageDTO {
    String user;
    Long usage;
}
