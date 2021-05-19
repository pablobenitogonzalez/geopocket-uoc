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
@Table(name = "liquec_spt")
public class Spt implements GeoPocketEntity, Comparable<Spt> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "depth")
    private Float depth;

    @Column(name = "spt_blow_counts")
    private Integer sptBlowCounts;

    @Column(name = "energy_ratio")
    private Float energyRatio;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="spt_result_id")
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
