package ru.hilariousstartups.javaskills.perfectstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.model.CurrentTickRequest;
import ru.hilariousstartups.javaskills.perfectstore.model.CurrentWorldResponse;

@Service
public class PerfectStoreService {

    private TickCounter tickCounter;
    private Integer totalIncome = 0;

    @Autowired
    public PerfectStoreService(TickCounter tickCounter) {
        this.tickCounter = tickCounter;
    }

    public CurrentWorldResponse tick(CurrentTickRequest request) {
        // todo реализвать проживание одного хода в магазине (предварительно применив или поставив в очередь менеджерские приказы)
        CurrentWorldResponse response = new CurrentWorldResponse();
        Integer currentTick = tickCounter.currentTick();

        Boolean gameover = currentTick.equals(tickCounter.getTickCount());
        if (!gameover) {
            currentTick = tickCounter.tick();
            gameover = currentTick.equals(tickCounter.getTickCount());
            totalIncome = currentTick * 18; // TODO Just to show progress. Needs to be implemented
            if (gameover) {
                System.out.println("Total income: " + totalIncome); // Для платформы mail.ru выводим итоговый результат в stdout
            }
        }
        response.setCurrentTick(currentTick);
        response.setGameOver(gameover);
        response.setIncome(totalIncome);


        return response;
    }



}
