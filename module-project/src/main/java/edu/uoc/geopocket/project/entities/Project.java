package edu.uoc.geopocket.project.entities;

import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.GeoPocketProjectEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "project")
public class Project implements GeoPocketProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "owner")
    private String user;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "organization")
    private String organization;

    @Embedded
    private Audit audit = new Audit();

}
