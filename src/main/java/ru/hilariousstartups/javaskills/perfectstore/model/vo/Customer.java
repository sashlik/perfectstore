package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.hilariousstartups.javaskills.perfectstore.model.CustomerMode;
import ru.hilariousstartups.javaskills.perfectstore.model.ProductInBasketDto;

import java.util.List;

@Data
@Schema(description = "Покуптель. Вначале покупатель находится в торговом зале и формирует корзину покупок. По окончании проходит на кассы и расплачивается")
public class Customer {

    @Schema(description = "Id покупателя", nullable = false)
    private Integer id;

    @Schema(description = "Фаза, в которой сейчас покупатель. Либо в торговом зале (in_hall), либо стоит в очереди на кассы (wait_checkout), либо уже расплачивается на кассе (at_checkout)", nullable = false)
    private CustomerMode mode;

    @Schema(description = "Продуктовая корзина", nullable = true)
    private List<ProductInBasket> basket;

}
