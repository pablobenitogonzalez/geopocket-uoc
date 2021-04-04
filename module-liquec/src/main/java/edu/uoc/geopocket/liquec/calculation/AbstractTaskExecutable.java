package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractTaskExecutable {

    protected LiquecTask task;

    public abstract void execute(final Liquec liquecProject, final Spt targetSpt);
}
