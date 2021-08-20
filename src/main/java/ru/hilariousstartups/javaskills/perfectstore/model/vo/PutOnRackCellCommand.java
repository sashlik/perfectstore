package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Команда выставить товар на полку. Товар можно выставить либо на пустую полку, либо на ту, на которой уже стоит данный товар.")
public class PutOnRackCellCommand {

    @Schema(description = "Id полки", required = true)
    private Integer rackCellId;

    @Schema(description = "Выставляемый товар", required = true)
    private Integer productId;

    @Schema(description = "Количество продукта, которое нужно выставить",  required = true)
    private Integer productQuantity;

    @Schema(description = "Цена, по которой продукт может купить покупатель. Если цена на продукт уже была выставлена, то это поле можно не передавать",  required = false)
    private Double sellPrice;

}
