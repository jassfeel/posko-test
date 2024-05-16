package daeucna.batch.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JobParamsRequest {
    private String paramKey;
    private String paramValue;
}