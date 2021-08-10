package ru.hilariousstartups.javaskills.perfectstore.service;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.hilariousstartups.javaskills.perfectstore.model.CheckoutLineDto;
import ru.hilariousstartups.javaskills.perfectstore.model.EmployeeDto;

import java.util.List;
import java.util.Map;
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
    private Double salaryCosts;
    private List<CheckoutLineDto> checkoutLines; // Кассы
    private List<EmployeeDto> employees; // Сотрудники

    public boolean isGameOver() {
        return currentTick.get() == tickCount;
    }

    public CheckoutLineDto findCheckoutLine(Integer number) {
        return checkoutLines.stream().filter(checkoutLineDto -> checkoutLineDto.getLineNumber().equals(number)).findFirst().orElse(null);
    }

    public EmployeeDto findEmployee(Integer id) {
        return employees.stream().filter(employeeDto -> employeeDto.getId().equals(id)).findFirst().orElse(null);
    }
}
