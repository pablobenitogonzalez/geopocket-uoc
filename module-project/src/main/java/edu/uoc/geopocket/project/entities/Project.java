package edu.uoc.geopocket.project.entities;

import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.ColumnDefinitions;
import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import edu.uoc.geopocket.common.entities.GeoPocketProjectEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "PROJECT")
public class Project implements GeoPocketProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "USER", columnDefinition = ColumnDefinitions.VARCHAR_300)
    private String user;

    @Column(name = "NAME", columnDefinition = ColumnDefinitions.VARCHAR_255)
    private String name;

    @Column(name = "LOCATION", columnDefinition = ColumnDefinitions.VARCHAR_300)
    private String location;

    @Column(name = "ORGANIZATION", columnDefinition = ColumnDefinitions.VARCHAR_300)
    private String organization;

    @Embedded
    private Audit audit = new Audit();

}
