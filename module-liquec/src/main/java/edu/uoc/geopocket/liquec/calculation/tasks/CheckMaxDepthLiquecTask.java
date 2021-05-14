package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.common.GeoPocketUtil;
import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.liquec.calculation.*;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.stream.Stream;

@Slf4j
@Component
public class CheckMaxDepthLiquecTask extends AbstractLiquecTaskExecutable {

    public CheckMaxDepthLiquecTask() {
        super(LiquecTask.CHECK_MAX_DEPTH);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {
        if (targetSpt.getDepth() > LiquecConstant.NO_CHECK_DEPTH.getValue(liquec.getCode())) {
            throw new LiquecTaskException(task, Result.ERROR, LiquecReason.MAX_DEPTH_CHECK, Collections.unmodifiableMap(Stream.of(
                    new AbstractMap.SimpleEntry<>("%depth%", String.valueOf(LiquecConstant.NO_CHECK_DEPTH.getValue(liquec.getCode()))))
                .collect(GeoPocketUtil.entriesToMap())));
        }
    }
}
