package ru.hilariousstartups.javaskills.perfectstore.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckoutLineDto {

    private Integer lineNumber;
    private EmployeeDto employeeDto;
    private CustomerDto customer;

    public CheckoutLineDto(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

}
