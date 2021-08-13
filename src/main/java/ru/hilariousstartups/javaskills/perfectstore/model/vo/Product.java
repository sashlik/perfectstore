package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Продукт (товар) находится либо на складе либо может быть выложен на полку в магазине. Один продукт может лежать только на одной полке. " +
        "Продукт на склад можно докупить по закупочной цене. И выставить на полку по любой цене. Чем дороже цена, тем сложнее покупателям купить товар.")
public class Product {

    @Schema(description = "Id продукта", nullable = false)
    private Integer id;

    @Schema(description = "Название продукта", nullable = false)
    private String name;

    @Schema(description = "Закупочная цена", nullable = false)
    private Double stockPrice;

    @Schema(description = "Количество товара на складе", nullable = false)
    private Integer inStock;

    @Schema(description = "Цена для покупателя. Если товар не выставлен на полку, то цена может быть не проставлена", nullable = true)
    private Double sellPrice;

}
