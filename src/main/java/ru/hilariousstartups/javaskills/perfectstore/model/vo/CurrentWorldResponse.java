package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Объект описывает текущее состояние мира")
public class CurrentWorldResponse {

    @Schema(description = "Количество тиков, которое будет длиться игра")
    private Integer tickCount;

    @Schema(description = "Текущее время игры (текущий тик или шаг)")
    private Integer currentTick;

    @Schema(description = "Текущая прибыль магазина")
    private Integer income;

    @Schema(description = "Расходы на зарплату")
    private Double salaryCosts;

    @Schema(description = "Если true значит игра завершена, и дальнейшие вызовы сервера не нужны")
    private Boolean gameOver;

    @Schema(description = "Кассы")
    private List<CheckoutLine> checkoutLines;

    @Schema(description = "Сотрудники")
    private List<Employee> employees;

    // todo реализовать всю модель мира и возвращать в этом объекте
}
