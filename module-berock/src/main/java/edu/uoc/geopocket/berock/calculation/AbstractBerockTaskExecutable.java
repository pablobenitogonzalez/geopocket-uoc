package edu.uoc.geopocket.berock.calculation;

import edu.uoc.geopocket.berock.entities.Berock;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.MathContext;

@Getter
@AllArgsConstructor
public abstract class AbstractBerockTaskExecutable {

    protected static final MathContext MATH_CONTEXT = new MathContext(16);

    protected BerockTask task;

    public abstract void execute(final Berock berock);
}
