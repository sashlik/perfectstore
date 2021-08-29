package ru.hilariousstartups.javaskills.perfectstore.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Кассовая линия. За ней может работать кассир, либо она может быть закрыта")
public class CheckoutLine {

    public CheckoutLine(Integer id) {
        this.id = id;
    }

    @Schema(description = "Номер кассовой линии", nullable = false)
    private Integer id;

    @Schema(description = "Сотрудник, работающий в данный момент за этой кассой. Если касса закрыта, то незаполнено", nullable = true)
    private Integer employeeId;

    @Schema(description = "Сотрудник, оплачивающий товар на этой кассе", nullable = true)
    private Integer customerId;

}
