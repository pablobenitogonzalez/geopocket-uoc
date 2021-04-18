package edu.uoc.geopocket.common.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@Embeddable
public class Audit {

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdOn = LocalDateTime.now();
        this.createdBy = getLogin();
        this.updatedOn = this.createdOn;
        this.updatedBy = this.createdBy;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedOn = LocalDateTime.now();
        this.updatedBy = getLogin();
    }

    private String getLogin() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).map(Principal::getName).orElse(null);
    }

}
