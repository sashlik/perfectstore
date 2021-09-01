package ru.hilariousstartups.javaskills.perfectstore.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentTickRequest;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentWorldResponse;
import ru.hilariousstartups.javaskills.perfectstore.service.DomainToViewMapper;
import ru.hilariousstartups.javaskills.perfectstore.service.PerfectStoreService;
import ru.hilariousstartups.javaskills.perfectstore.service.WorldContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class PerfectStoreEndpoint {

    private PerfectStoreService perfectStoreService;
    private ApplicationContext applicationContext;

    @Autowired
    public PerfectStoreEndpoint(PerfectStoreService perfectStoreService, ApplicationContext applicationContext) {
        this.perfectStoreService = perfectStoreService;
        this.applicationContext = applicationContext;
    }

    @Operation(summary = "Получить текущее состояние мира. Игра начинается с первичной загрузки данных о мире.")
    @GetMapping("/loadWorld")
    public CurrentWorldResponse loadWorld() {
        try {
            return perfectStoreService.currentWorldResponse();
        } catch (Exception e) {
            perfectStoreService.logResult("ERR", 0d, null, e.getMessage());
            SpringApplication.exit(applicationContext, () -> 0);
            System.exit(0);
            return null;
        }
    }

    @Operation(summary = "Прожить еще одну минуту. На вход передаются управленческие решения менеджмента магазина (если нужны), проживается еще одна минута и возвращается состояние мира после прожитой минуты")
    @PostMapping("/tick")
    public CurrentWorldResponse tick(@RequestBody CurrentTickRequest request) {
        try {
            CurrentWorldResponse tick = perfectStoreService.tick(request);
            termiateIfGameOver(tick);
            return tick;
        } catch (Exception e) {
            perfectStoreService.logResult("ERR", 0d, null, e.getMessage());
            SpringApplication.exit(applicationContext, () -> 0);
            System.exit(0);
            return null;
        }
    }

    private void termiateIfGameOver(CurrentWorldResponse tick) {
        if (tick.getGameOver()) {
            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
            scheduledThreadPool.schedule(() -> {
                SpringApplication.exit(applicationContext, () -> 0);
                System.exit(0);
            }, 3, TimeUnit.SECONDS);
        }
    }

}
