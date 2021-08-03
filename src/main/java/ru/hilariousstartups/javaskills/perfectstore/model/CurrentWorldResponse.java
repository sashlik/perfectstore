package ru.hilariousstartups.javaskills.perfectstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Объект описывает текущее состояние мира")
public class CurrentWorldResponse {

    @Schema(description = "Текущее время игры (текущий тик или шаг)")
    private Integer currentTick;

    @Schema(description = "Текущая прибыль магазина")
    private Integer income;

    @Schema(description = "Если true значит игра завершена, и дальнейшие вызовы сервера не нужны")
    private Boolean gameOver;

    // todo реализовать всю модель мира и возвращать в этом объекте
}
