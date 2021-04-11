package edu.uoc.geopocket.security.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetails {
  private String name;
  private List<GeoPocketRole> roles;

  public List<String> getUserRoles() {
    return Optional.ofNullable(roles).orElse(Collections.emptyList()).stream()
        .map(GeoPocketRole::getRole).collect(Collectors.toList());
  }
}
