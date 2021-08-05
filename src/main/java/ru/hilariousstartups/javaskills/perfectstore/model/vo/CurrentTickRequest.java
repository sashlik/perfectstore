package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Объект описывает текущие менеджерские решения, которые нужно совершить в этом тике. Если никаких решений предпринимать не нужно, передается пустой объект")
public class CurrentTickRequest {

    // TODO реализовать модель действий менеджмента магазина, которые влияют на происходящее в игре

}
