package ru.hilariousstartups.javaskills.perfectstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TickCounter {

    private Integer tickCount;
    private AtomicInteger currentTick = new AtomicInteger(0);

    @Autowired
    public TickCounter(@Value("${gamedays:7}") Integer gameDays) {
        tickCount = gameDays * 24 * 60; // tick = 1 minute
    }

    public Integer getTickCount() {
        return tickCount;
    }

    public Integer tick() {
       return currentTick.incrementAndGet();
    }

    public Integer currentTick() {
        return currentTick.get();
    }

}
