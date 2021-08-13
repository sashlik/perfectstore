package ru.hilariousstartups.javaskills.perfectstore.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Команда на снятие сотрудника с кассы и начало отдыха.")
public class SetOffCheckoutLineCommand {

    @Schema(description = "Id сотрудника", required = true)
    private Integer employeeId;

}
