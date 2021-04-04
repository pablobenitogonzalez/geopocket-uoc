package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.common.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class LiquecTaskException extends RuntimeException {
    private LiquecTask task;
    private Result result;
    private LiquecReason reason;
    private Map<String, String> params;

    public LiquecTaskException(final LiquecTask task, final Result result, final LiquecReason reason) {
        this.task = task;
        this.result = result;
        this.reason = reason;
        this.params = new HashMap<>();
    }

    String getReasonMessage() {
        String reasonMessage = this.reason.getMessage();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            reasonMessage = reasonMessage.replace(entry.getKey(), entry.getValue());
        }
        return reasonMessage;
    }
}
