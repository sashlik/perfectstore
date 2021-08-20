package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Продуктовая полка. На полке может находиться товар только одного вида, либо она может быть пустой. Полка может хранить " +
        "определенное максимальное количество товаров. Также полки разнятся по заметности для покупателя (от 1 - самая незаметная, до 5 - максимально на виду")
public class RackCell {

    @Schema(description = "Id полки", nullable = false)
    private Integer id;

    @Schema(description = "Заметность полки для покупателя (1 - самая незаметная, 5 - самая заметная)", nullable = false)
    private Integer visibility;

    @Schema(description = "Максимальное количество товара на полке", nullable = false)
    private Integer capacity = 10;

    @Schema(description = "Id продукта, стоящего на полке (если полка пустая, то не заполняется)", nullable = true)
    private Integer productId;

    @Schema(description = "Название продукта, стоящего на полке (если полка пустая, то не заполняется)", nullable = true)
    private String productName;

    @Schema(description = "Количество продукта, стоящего на полке", nullable = true)
    private Integer productQuantity;

}
