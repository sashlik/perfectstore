package ru.hilariousstartups.javaskills.perfectstore.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hilariousstartups.javaskills.perfectstore.model.CurrentTickRequest;
import ru.hilariousstartups.javaskills.perfectstore.model.CurrentWorldResponse;
import ru.hilariousstartups.javaskills.perfectstore.model.InitialWorldResponse;
import ru.hilariousstartups.javaskills.perfectstore.service.PerfectStoreService;
import ru.hilariousstartups.javaskills.perfectstore.service.TickCounter;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class PerfectStoreEndpoint {

    private TickCounter tickCounter;
    private PerfectStoreService perfectStoreService;

    @Autowired
    public PerfectStoreEndpoint(TickCounter tickCounter, PerfectStoreService perfectStoreService) {
        this.tickCounter = tickCounter;
        this.perfectStoreService = perfectStoreService;
    }

    @Operation(summary = "Игра начинается с первичной загрузки данных о мире.")
    @GetMapping("/loadWorld")
    public InitialWorldResponse loadWorld() {
        InitialWorldResponse response = new InitialWorldResponse();
        response.setTickCount(tickCounter.getTickCount());
        return response;
    }

    @Operation(summary = "Прожить еще одну минуту. На вход передаются управленческие решения менеджмента магазина (если нужны), проживается еще одна минута и возвращается состояние мира после прожитой минуты")
    @GetMapping("/tick")
    public CurrentWorldResponse tick(CurrentTickRequest request) {
        return perfectStoreService.tick(request);
    }

}
