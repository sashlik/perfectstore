package ru.hilariousstartups.javaskills.perfectstore.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Команда на отправку сотрудника на кассу.")
public class SetOnCheckoutLineCommand {

    @Schema(description = "Номер кассы", required = true)
    private Integer checkoutLineId;

    @Schema(description = "Id сотрудника", required = true)
    private Integer employeeId;

}
