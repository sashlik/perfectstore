package ru.hilariousstartups.javaskills.perfectstore.service;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.hilariousstartups.javaskills.perfectstore.model.*;

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
    private Integer income;
    private Double salaryCosts;
    private Double stockCosts;

    private List<CheckoutLineDto> checkoutLines; // Кассы
    private List<EmployeeDto> employees; // Сотрудники
    private List<ProductDto> stock; // Склад товаров
    private List<RackCellDto> rackCells; // Продуктовые полки
    private List<CustomerDto> customers; // покупатели


    public boolean isGameOver() {
        return currentTick.get() == tickCount;
    }

    public CheckoutLineDto findCheckoutLine(Integer number) {
        return checkoutLines.stream().filter(checkoutLineDto -> checkoutLineDto.getLineNumber().equals(number)).findFirst().orElse(null);
    }

    public EmployeeDto findEmployee(Integer id) {
        return employees.stream().filter(employeeDto -> employeeDto.getId().equals(id)).findFirst().orElse(null);
    }

    public ProductDto findProduct(Integer id) {
        return stock.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public RackCellDto findRackCell(Integer id) {
        return rackCells.stream().filter(rack -> rack.getId().equals(id)).findFirst().orElse(null);
    }
}
