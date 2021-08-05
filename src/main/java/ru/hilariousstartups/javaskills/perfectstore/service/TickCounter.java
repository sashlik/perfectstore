package ru.hilariousstartups.javaskills.perfectstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hilariousstartups.javaskills.perfectstore.config.ExternalConfig;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TickCounter {

    private WorldContext worldContext;

    @Autowired
    public TickCounter(WorldContext worldContext) {
        this.worldContext = worldContext;
    }


    public Integer getTickCount() {
        return worldContext.getTickCount();
    }

    public Integer tick() {
       return worldContext.getCurrentTick().incrementAndGet();
    }

    public Integer currentTick() {
        return worldContext.getCurrentTick().get();
    }

}
