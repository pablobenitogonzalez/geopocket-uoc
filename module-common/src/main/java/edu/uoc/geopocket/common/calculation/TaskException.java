package edu.uoc.geopocket.common.calculation;

import edu.uoc.geopocket.common.Result;

import java.util.Map;

public interface TaskException<T extends Describable, R extends Reason> {
    T getTask();
    Result getResult();
    R getReason();
    Map<String, String> getParams();
    String getReasonMessage();
}
