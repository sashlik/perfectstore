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

    @Autowired
    public ExternalConfig(@Value("${gamedays:7}") Integer gameDays,@Value("${storesize:medium}")  String storeSize) {
        this.gameDays = gameDays;
        this.storeSize = storeSize;
    }
}
