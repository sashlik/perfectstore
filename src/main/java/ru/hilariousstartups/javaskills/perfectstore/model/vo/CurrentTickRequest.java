package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Объект описывает текущие менеджерские решения, которые нужно совершить в этом тике. Если никаких решений предпринимать не нужно, передается пустой объект")
public class CurrentTickRequest {

    // TODO реализовать модель действий менеджмента магазина, которые влияют на происходящее в игре

    @Schema(description = "Команды на найм новых сотрудников", required = false)
    private List<HireEmployeeCommand> hireEmployeeCommands;

    @Schema(description = "Команды на увольнение сотрудников. Увольнение происходит только после отдыха от рабочей смены", required = false)
    private List<FireEmployeeCommand> fireEmployeeCommands;

}
