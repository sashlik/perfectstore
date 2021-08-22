package ru.hilariousstartups.javaskills.perfectstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import ru.hilariousstartups.javaskills.perfectstore.service.EmployeeService;
import ru.hilariousstartups.javaskills.perfectstore.service.PerfectStoreService;

@Data
public class EmployeeDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer experience;
    private Integer salary;
    private CheckoutLineDto checkoutLine;
    private boolean needToFire; // Сотрудник помечен на увольнение и должен быть уволен как только отдохнет после смены
                                // (это сделано для того чтоб вместо оплачиваемого отдыха не увольняли и не нанимали нового)
    private boolean needsOffLine; // Сотрудник либо отработал смену либо был помечен на снятие с кассы.

    private Integer workTime = 0;
    private Integer restTime = EmployeeService.REST_TIME; // Создаем новых кассиров уже "отдохнувшими"

    public String fullName() {
        return firstName + " " + lastName + "(" + id + ")";
    }




}
