package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Продукт в корзине покупателя.")
public class ProductInBasket {

    @Schema(description = "Id продукта", nullable = false)
    private Integer id;

    @Schema(description = "Название продукта", nullable = false)
    private String productName;

    @Schema(description = "Цена по которой покупатель приобретает товар (фиксируется на момент взятия товара с полки)", nullable = true)
    private Double priсe;

    @Schema(description = "Количество покупаемого товара", nullable = false)
    private Integer productCount;

}
