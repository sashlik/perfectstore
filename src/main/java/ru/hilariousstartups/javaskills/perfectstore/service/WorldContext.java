package ru.hilariousstartups.javaskills.perfectstore.service;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.hilariousstartups.javaskills.perfectstore.model.CheckoutLineDto;
import ru.hilariousstartups.javaskills.perfectstore.model.EmployeeDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Объект хранит в себе текущее полное состояние игрового мира. Состояние касс, кассиров, продуктов, покупателей итд
 */
@Component
@Data
public class WorldContext {

    private AtomicInteger currentTick;
    private Integer tickCount;
    private Integer totalIncome;
    private List<CheckoutLineDto> checkoutLines; // Кассы
    private List<EmployeeDto> employees; // Сотрудники

    public boolean isGameOver() {
        return currentTick.get() == tickCount;
    }

}
