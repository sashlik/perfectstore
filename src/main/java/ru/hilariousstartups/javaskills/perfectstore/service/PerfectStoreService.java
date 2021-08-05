package ru.hilariousstartups.javaskills.perfectstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentTickRequest;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentWorldResponse;

@Service
public class PerfectStoreService {

    private Integer totalIncome = 0;
    private WorldContext worldContext;
    private DomainToViewMapper domainToViewMapper;

    @Autowired
    public PerfectStoreService(WorldContext worldContext, DomainToViewMapper domainToViewMapper) {
        this.worldContext = worldContext;
        this.domainToViewMapper = domainToViewMapper;
    }

    public CurrentWorldResponse tick(CurrentTickRequest request) {
        // todo реализвать проживание одного хода в магазине (предварительно применив или поставив в очередь менеджерские приказы)


        if (!worldContext.isGameOver()) {
            worldContext.getCurrentTick().incrementAndGet();
            totalIncome = worldContext.getCurrentTick().get() * 18; // TODO Just to show progress. Needs to be implemented
            if (worldContext.isGameOver()) {
                System.out.println("Total income: " + totalIncome); // Для платформы mail.ru выводим итоговый результат в stdout
            }
        }

        CurrentWorldResponse response = domainToViewMapper.currentWorldResponse();
        response.setIncome(totalIncome);


        return response;
    }



}
