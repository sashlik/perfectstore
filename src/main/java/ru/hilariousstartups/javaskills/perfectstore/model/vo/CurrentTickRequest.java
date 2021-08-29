package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Объект описывает текущие менеджерские решения, которые нужно совершить в этом тике. " +
        "Если никаких решений предпринимать не нужно, передается пустой объект")
public class CurrentTickRequest {

    @Schema(description = "Команды на увольнение сотрудников. Увольнение происходит только после отдыха от рабочей смены", required = false)
    private List<FireEmployeeCommand> fireEmployeeCommands;

    @Schema(description = "Команды на найм новых сотрудников", required = false)
    private List<HireEmployeeCommand> hireEmployeeCommands;

    @Schema(description = "Команды для начала работы сотрудника за кассой", required = false)
    private List<SetOnCheckoutLineCommand> setOnCheckoutLineCommands;

    @Schema(description = "Команды для снятия сотрудника с кассы", required = false)
    private List<SetOffCheckoutLineCommand> setOffCheckoutLineCommands;

    @Schema(description = "Команды на закупку товара у поставщика на склад. При покупке товара на склад вы оплачиваете услуги транспортной компании в размере 5000р. Поэтому старайтесь заказывать реже и по многу", required = false)
    private List<BuyStockCommand> buyStockCommands;

    @Schema(description = "Команды на снятие продукта с полки", required = false)
    private List<PutOffRackCellCommand> putOffRackCellCommands;

    @Schema(description = "Команды на выставление товаров на полки", required = false)
    private List<PutOnRackCellCommand> putOnRackCellCommands;

    @Schema(description = "Команды установки цен", required = false)
    private List<SetPriceCommand> setPriceCommands;


}
