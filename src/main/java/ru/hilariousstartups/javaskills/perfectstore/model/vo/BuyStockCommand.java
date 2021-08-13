package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Команда на закупку товара у поставщика на склад.")
public class BuyStockCommand {

    @Schema(description = "Id товара", required = true)
    private Integer productId;

    @Schema(description = "Количество товара", required = true)
    private Integer quantity;


}
