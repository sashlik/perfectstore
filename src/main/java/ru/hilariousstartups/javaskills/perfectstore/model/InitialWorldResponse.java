package ru.hilariousstartups.javaskills.perfectstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Объект описывает начальное состояние мира при старте игры")
public class InitialWorldResponse {

    @Schema(description = "Количество тиков, которое будет длиться игра")
    private Integer tickCount;

    // todo реализовать всю модель мира и возвращать в этом объекте

}
