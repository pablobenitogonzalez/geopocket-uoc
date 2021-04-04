package edu.uoc.geopocket.liquec.repositories;

import edu.uoc.geopocket.common.repositories.ToolRepository;
import edu.uoc.geopocket.liquec.entities.Liquec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiquecRepository extends JpaRepository<Liquec, Long>, ToolRepository<Liquec> {
}
