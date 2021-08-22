package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Объект описывает текущее состояние мира")
public class CurrentWorldResponse {

    @Schema(description = "Количество тиков, которое будет длиться игра", nullable = false)
    private Integer tickCount;

    @Schema(description = "Текущее время игры (текущий тик или шаг)", nullable = false)
    private Integer currentTick;

    @Schema(description = "Текущая прибыль магазина", nullable = false)
    private Integer income;

    @Schema(description = "Расходы на зарплату", nullable = false)
    private Double salaryCosts;

    @Schema(description = "Расходы на закупку товара", nullable = false)
    private Double stockCosts;

    @Schema(description = "Если true значит игра завершена, и дальнейшие вызовы сервера не нужны", nullable = false)
    private Boolean gameOver;

    @Schema(description = "Кассы", nullable = false)
    private List<CheckoutLine> checkoutLines;

    @Schema(description = "Сотрудники", nullable = false)
    private List<Employee> employees;

    @Schema(description = "Кадровое агенство. Справочная информация о том, каких сотрудников можно нанять и по какой ставке", nullable = false)
    private List<EmployeeRecruitmentOffer> recruitmentAgency;

    @Schema(description = "Склад с товарами", nullable = false)
    private List<Product> stock;

    @Schema(description = "Продуктовые полки", nullable = false)
    private List<RackCell> rackCells;

}
