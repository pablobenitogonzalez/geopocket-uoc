package edu.uoc.geopocket.berock.calculation;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.common.calculation.TaskException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class BerockTaskException extends RuntimeException implements TaskException<BerockTask, BerockReason> {
    private BerockTask task;
    private Result result;
    private BerockReason reason;
    private Map<String, String> params;

    public BerockTaskException(final BerockTask task, final Result result, final BerockReason reason) {
        this.task = task;
        this.result = result;
        this.reason = reason;
        this.params = new HashMap<>();
    }

    public String getReasonMessage() {
        String reasonMessage = this.reason.getMessage();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            reasonMessage = reasonMessage.replace(entry.getKey(), entry.getValue());
        }
        return reasonMessage;
    }
}
