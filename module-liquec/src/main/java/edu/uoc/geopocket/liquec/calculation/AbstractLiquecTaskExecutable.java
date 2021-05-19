package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.MathContext;

@Getter
@AllArgsConstructor
public abstract class AbstractLiquecTaskExecutable {

    protected static final MathContext MATH_CONTEXT = new MathContext(16);

    protected LiquecTask task;

    public abstract void execute(final Liquec liquec, final Spt targetSpt);
}
