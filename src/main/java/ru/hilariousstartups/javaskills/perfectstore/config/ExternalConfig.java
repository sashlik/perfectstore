package ru.hilariousstartups.javaskills.perfectstore.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ExternalConfig {

    private Integer gameDays;
    private String storeSize; // small, medium, big
    private Boolean pregenerate; // fill stock, fill racks, hire employees before game begins
    private String resultPath;

    @Autowired
    public ExternalConfig(@Value("${gamedays:7}") Integer gameDays,
                          @Value("${storesize:small}")  String storeSize,
                          @Value("${pregenerate:false}") Boolean pregenerate,
                          @Value("${RESULT_LOCATION:/Users/Guest/Public/}") String resultPath) {
        this.gameDays = gameDays;
        this.pregenerate = pregenerate;
        this.storeSize = storeSize;
        this.resultPath = resultPath;
    }
}
