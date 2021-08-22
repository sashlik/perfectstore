package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRecruitmentOffer {

    private String employeeType;
    private String experience;
    private Integer salary;

}
