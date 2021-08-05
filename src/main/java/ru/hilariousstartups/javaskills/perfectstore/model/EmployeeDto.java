package ru.hilariousstartups.javaskills.perfectstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
public class EmployeeDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer experience;
    private Integer salary;

}
