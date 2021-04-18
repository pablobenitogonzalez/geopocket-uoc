package edu.uoc.geopocket.security.helper;

import edu.uoc.geopocket.security.common.GeoPocketRole;
import edu.uoc.geopocket.security.common.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SecurityContextHelper {

  public UserDetails getUserDetails() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return UserDetails.builder().roles(Collections.emptyList()).build();
    }
    final List<GeoPocketRole> roles = Optional.ofNullable(authentication.getAuthorities())
        .orElse(Collections.emptyList()).stream().map(e -> GeoPocketRole.getRole(e.getAuthority()))
        .collect(Collectors.toList());
    return UserDetails.builder().name(authentication.getName()).roles(roles).build();
  }

  public String getUserName() {
    return Optional.ofNullable(getUserDetails().getName()).orElse(null);
  }

  public boolean hasRole(final GeoPocketRole role) {
    return getUserDetails().getRoles().contains(role);
  }

}
