package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Команда на найм нового сотрудника.")
public class HireEmployeeCommand {

    @Schema(description = "Новичок (опыт от 10% до 30%), обычного кассир (опыт от 30% до 70%) или опытный (70% до 100%)", nullable = false)
    private EmployeeExperience experience;

    @Schema(description = "Если требуется посадить нового сотрудника за кассу, указать номер кассы", nullable = true)
    private Integer checkoutLineId;



}
