package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Команда для задания (или изменения) цены товара для покупателя.")
public class SetPriceCommand {

    @Schema(description = "Id товара", required = true)
    private Integer productId;

    @Schema(description = "Цена, по которой продукт может купить покупатель.",  required = true)
    private Double sellPrice;

}
