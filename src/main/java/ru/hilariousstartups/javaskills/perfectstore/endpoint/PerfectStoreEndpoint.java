package ru.hilariousstartups.javaskills.perfectstore.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentTickRequest;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentWorldResponse;
import ru.hilariousstartups.javaskills.perfectstore.service.DomainToViewMapper;
import ru.hilariousstartups.javaskills.perfectstore.service.PerfectStoreService;
import ru.hilariousstartups.javaskills.perfectstore.service.WorldContext;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class PerfectStoreEndpoint {

    private PerfectStoreService perfectStoreService;
    private WorldContext worldContext;
    private DomainToViewMapper domainToViewMapper;

    @Autowired
    public PerfectStoreEndpoint(WorldContext worldContext,
                                PerfectStoreService perfectStoreService,
                                DomainToViewMapper domainToViewMapper) {
        this.worldContext = worldContext;
        this.perfectStoreService = perfectStoreService;
        this.domainToViewMapper = domainToViewMapper;
    }

    @Operation(summary = "Получить текущее состояние мира. Игра начинается с первичной загрузки данных о мире.")
    @GetMapping("/loadWorld")
    public CurrentWorldResponse loadWorld() {
        return domainToViewMapper.currentWorldResponse();
    }

    @Operation(summary = "Прожить еще одну минуту. На вход передаются управленческие решения менеджмента магазина (если нужны), проживается еще одна минута и возвращается состояние мира после прожитой минуты")
    @GetMapping("/tick")
    public CurrentWorldResponse tick(CurrentTickRequest request) {
        return perfectStoreService.tick(request);
    }

}
