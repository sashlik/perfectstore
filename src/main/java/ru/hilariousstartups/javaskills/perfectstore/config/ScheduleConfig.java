package ru.hilariousstartups.javaskills.perfectstore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.hilariousstartups.javaskills.perfectstore.service.PerfectStoreService;
import ru.hilariousstartups.javaskills.perfectstore.service.WorldContext;

@EnableScheduling
@Configuration
@Slf4j
public class ScheduleConfig {

    private ApplicationContext applicationContext;
    private WorldContext worldContext;
    private PerfectStoreService perfectStoreService;

    public ScheduleConfig(ApplicationContext applicationContext, WorldContext worldContext, PerfectStoreService perfectStoreService) {
        this.applicationContext = applicationContext;
        this.worldContext = worldContext;
        this.perfectStoreService = perfectStoreService;
    }

    @Scheduled(initialDelay = 30000, fixedDelay = 30000)
    public void waitPlayer() {
        if (!worldContext.isPlayerConnected()) {
            log.debug("Игрок не подключился");

            perfectStoreService.logResult("ERR", 0d, null, "Игрок не подключился к серверу (превышен лимит ожидания: 30 секунд). Игра отменена.");
            SpringApplication.exit(applicationContext, () -> 0);
            System.exit(0);
        }
    }

    @Scheduled(initialDelay = 300000, fixedDelay = 300000)
    public void waitGameTimout() {
        log.debug("Превышена максимальная длительность одной игры");
        perfectStoreService.logResult("ERR", 0d, null, "Превышена максимальная длительность одной игры: 300 секунд. Пройдено "+worldContext.getCurrentTick().get()+" из "+worldContext.getTickCount()+" тиков.");
        SpringApplication.exit(applicationContext, () -> 0);
        System.exit(0);
    }

}
