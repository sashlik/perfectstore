package ru.hilariousstartups.javaskills.perfectstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CheckoutLine;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentWorldResponse;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.Employee;

import java.util.stream.Collectors;

@Service
public class DomainToViewMapper {

    private WorldContext worldContext;

    @Autowired
    public DomainToViewMapper(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public CurrentWorldResponse currentWorldResponse() {
        CurrentWorldResponse response = new CurrentWorldResponse();
        response.setTickCount(worldContext.getTickCount());
        response.setCurrentTick(worldContext.getCurrentTick().get());
        response.setGameOver(worldContext.isGameOver());
        response.setIncome(worldContext.getTotalIncome());
        response.setSalaryCosts(worldContext.getSalaryCosts());
        response.setCheckoutLines(worldContext.getCheckoutLines().stream().map(checkoutLineDto -> {
            CheckoutLine checkoutLine = new CheckoutLine();
            checkoutLine.setLineNumber(checkoutLineDto.getLineNumber());
            checkoutLine.setEmployeeId(checkoutLineDto.getEmployeeDto() != null ? checkoutLineDto.getEmployeeDto().getId() : null);
            return checkoutLine;
        }).collect(Collectors.toList()));

        response.setEmployees(worldContext.getEmployees().stream().map(employeeDto -> {
            Employee employee = new Employee();
            employee.setId(employeeDto.getId());
            employee.setFirstName(employeeDto.getFirstName());
            employee.setLastName(employeeDto.getLastName());
            employee.setSalary(employeeDto.getSalary());
            employee.setExperience(employeeDto.getExperience());
            return employee;
        }).collect(Collectors.toList()));
        return response;
    }

}
