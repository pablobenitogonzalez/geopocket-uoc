package edu.uoc.geopocket.project.entities;

import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.ColumnDefinitions;
import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "PROJECT")
public class Project implements GeoPocketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "ROLE_USER", columnDefinition = ColumnDefinitions.VARCHAR2_300)
    private String user;

    @Column(name = "NAME", columnDefinition = ColumnDefinitions.VARCHAR2_255)
    private String name;

    @Column(name = "LOCATION", columnDefinition = ColumnDefinitions.VARCHAR2_300)
    private String location;

    @Column(name = "ORGANIZATION", columnDefinition = ColumnDefinitions.VARCHAR2_300)
    private String organization;

    @Embedded
    private Audit audit = new Audit();

}
