package edu.uoc.geopocket.berock.entities;

import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.common.entities.GeoPocketEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "BEROCK_JOINT")
public class Joint implements GeoPocketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "SPACING")
    private Float spacing;

    @Column(name = "FAMILY")
    private String family;

    @Embedded
    private Audit audit = new Audit();

}
