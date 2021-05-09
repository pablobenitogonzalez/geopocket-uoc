package edu.uoc.geopocket.common;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usage {
    String user;
    Long usage;
}
