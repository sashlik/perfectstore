package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Кассир. Работать может не больше 8 часов подряд, после смены должен отдыхать 16 часов." +
        "Сотрудник имеет показатель опыта от 10% до 100%. Чем больше опыт, тем быстрее он обслуживает клиента, но тем больше получает ЗП." +
        "Можно нанять новичка (опыт от 10% до 30%), обычного кассира (опыт от 30% до 70%) или опытного (70% до 100%). Новичок получает 150р в час, " +
        "обычный кассир 270р в час, опытный 420р в час. Зарплата расчитывается с округлением до часа в пользу сотрудника. " +
        "Сотрудника нельзя уволить пока он не отдохнул после смены и пока он на смене.")
public class Employee {

    @Schema(description = "Табельный номер сотрудника", nullable = false)
    private Integer id;

    @Schema(description = "Имя сотрудника", nullable = false)
    private String firstName;

    @Schema(description = "Имя сотрудника", nullable = false)
    private String lastName;

    @Schema(description = "Опыт сотрудника", nullable = false)
    private Integer experience;

    @Schema(description = "Зарплата сотрудника в час", nullable = false)
    private Integer salary;


}
