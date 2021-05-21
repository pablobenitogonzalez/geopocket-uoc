package edu.uoc.geopocket.security.common;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GeoPocketRole {

    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_PROFESSOR("ROLE_PROFESSOR");

    private final String role;

    GeoPocketRole(final String role) {
        this.role = role;
    }

    public String getRoleName() {
        return role.replace("ROLE_", "");
    }

    public static GeoPocketRole getRole(final String role) {
        return Arrays.stream(GeoPocketRole.values()).filter(geoPocketRole -> geoPocketRole.getRole().equals(role))
            .findFirst().orElseThrow(() -> new SecurityException(String.format("Role [%s] not found", role)));
    }
}
