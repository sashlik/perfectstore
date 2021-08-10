package ru.hilariousstartups.javaskills.perfectstore.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Команда на увольнение сотрудника.")
public class FireEmployeeCommand {

    @Schema(description = "Id увольняемого сотрудника", required = true)
    private Integer employeeId;

}
