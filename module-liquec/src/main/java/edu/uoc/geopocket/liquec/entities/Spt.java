package edu.uoc.geopocket.liquec.entities;

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
@Table(name = "LIQUEC_SPT")
public class Spt implements GeoPocketEntity, Comparable<Spt> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "DEPTH")
    private Float depth;

    @Column(name = "SPT_BLOW_COUNTS")
    private Integer sptBlowCounts;

    @Column(name = "ENERGY_RATIO")
    private Float energyRatio;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="SPT_RESULT_ID")
    private SptResult sptResult;

    @Embedded
    private Audit audit = new Audit();

    public int compareTo(final Spt spt) {
        if (this.depth.equals(spt.getDepth())) {
            return 0;
        }
        return this.depth < spt.getDepth() ? -1 : 1;
    }
}
