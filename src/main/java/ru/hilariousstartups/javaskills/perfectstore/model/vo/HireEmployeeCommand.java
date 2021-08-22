package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Команда на найм нового сотрудника.")
public class HireEmployeeCommand {

    @Schema(description = "Опытность сотрудника. Варианты можно увидеть в разделе recruitmentAgency", required = true)
    private EmployeeExperience experience;

    @Schema(description = "Если требуется посадить нового сотрудника за кассу, указать номер кассы", required = false)
    private Integer checkoutLineId;

}
