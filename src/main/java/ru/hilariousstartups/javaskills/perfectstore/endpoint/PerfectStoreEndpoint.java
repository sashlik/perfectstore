package ru.hilariousstartups.javaskills.perfectstore.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentTickRequest;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentWorldResponse;
import ru.hilariousstartups.javaskills.perfectstore.service.DomainToViewMapper;
import ru.hilariousstartups.javaskills.perfectstore.service.PerfectStoreService;
import ru.hilariousstartups.javaskills.perfectstore.service.WorldContext;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class PerfectStoreEndpoint {

    private PerfectStoreService perfectStoreService;

    @Autowired
    public PerfectStoreEndpoint(PerfectStoreService perfectStoreService) {
        this.perfectStoreService = perfectStoreService;
    }

    @Operation(summary = "Получить текущее состояние мира. Игра начинается с первичной загрузки данных о мире.")
    @GetMapping("/loadWorld")
    public CurrentWorldResponse loadWorld() {
        return perfectStoreService.currentWorldResponse();
    }

    @Operation(summary = "Прожить еще одну минуту. На вход передаются управленческие решения менеджмента магазина (если нужны), проживается еще одна минута и возвращается состояние мира после прожитой минуты")
    @PostMapping("/tick")
    public CurrentWorldResponse tick(@RequestBody CurrentTickRequest request) {
        return perfectStoreService.tick(request);
    }

}
