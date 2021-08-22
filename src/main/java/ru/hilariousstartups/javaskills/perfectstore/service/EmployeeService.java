package ru.hilariousstartups.javaskills.perfectstore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.model.CheckoutLineDto;
import ru.hilariousstartups.javaskills.perfectstore.model.EmployeeDto;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    public static final int REST_TIME = 16 * 60;
    public static final int MAX_WORK_TIME = 8 * 60;

    private WorldContext worldContext;
    private EmployeeGenerator employeeGenerator;

    @Autowired
    public EmployeeService(WorldContext worldContext,
                           EmployeeGenerator employeeGenerator) {
        this.worldContext = worldContext;
        this.employeeGenerator = employeeGenerator;
    }

    public void handleOnLineCommands(List<SetOnCheckoutLineCommand> onLineCommands) {
        if (onLineCommands != null) {
            onLineCommands.forEach(command -> {
                EmployeeDto employee = worldContext.findEmployee(command.getEmployeeId());
                CheckoutLineDto checkoutLine = worldContext.findCheckoutLine(command.getCheckoutLineId());
                if (employee != null && checkoutLine != null) {
                    startWork(employee, checkoutLine);
                }
            });
        }
    }

    public void handleOffLineCommands(List<SetOffCheckoutLineCommand> offLineCommands) {
        if (offLineCommands != null) {
            offLineCommands.forEach(command -> {
                EmployeeDto employee = worldContext.findEmployee(command.getEmployeeId());
                if (employee != null) {
                    stopWork(employee);
                }
            });
        }
    }

    public void handleFireEmployeeCommands(List<FireEmployeeCommand> fireEmployeeCommands) {
        if (fireEmployeeCommands != null) {
            fireEmployeeCommands.forEach(fec -> {
                EmployeeDto fireCandidate = worldContext.findEmployee(fec.getEmployeeId());
                if (fireCandidate != null) {
                    log.info(fireCandidate.fullName() + " будет уволена как только отдохнет положенное время");
                    fireCandidate.setNeedToFire(true);
                    stopWork(fireCandidate);
                }
            });
        }
    }

    public void handleHireEmployeeCommands(List<HireEmployeeCommand> hireEmployeeCommands) {
        if (hireEmployeeCommands != null) {
            hireEmployeeCommands.forEach(hec -> {
                EmployeeDto employee = employeeGenerator.generate(hec.getExperience());
                worldContext.getEmployees().add(employee);

                if (hec.getCheckoutLineId() != null) {
                    CheckoutLineDto checkoutLine = worldContext.findCheckoutLine(hec.getCheckoutLineId());
                    if (checkoutLine != null) {
                        startWork(employee, checkoutLine);
                    }
                }
            });
        }
    }

    public void calcEmployeeWorkload(CurrentTickRequest request) {

        worldContext.getEmployees().forEach(employee -> {
            if (employee.getCheckoutLine() == null) {
                // Сотрудник не работает. Увеличиваем время, проведенное им в режиме отдыха
                increaseRestTime(employee);
            }
            else {
                if (employee.getWorkTime() >= MAX_WORK_TIME) {
                    // Сотрудник отработал смену, снимаем его с кассы
                    stopWork(employee);
                }
                else {
                    increaseWorkTime(employee);
                }
            }
            double salaryPerTick = employee.getSalary() / 60d;
            worldContext.setSalaryCosts(worldContext.getSalaryCosts() + salaryPerTick);
        });

        // Увольняем тех, кого нужно уволить и кто отдохнул положенное
        List<EmployeeDto> toFire = worldContext.getEmployees().stream()
                .filter(employee -> employee.isNeedToFire() && employee.getRestTime() >= REST_TIME).collect(Collectors.toList());
        worldContext.getEmployees().removeAll(toFire);
        toFire.forEach(employee ->  log.info(employee.fullName() + " уволена."));
    }

    public void increaseWorkTime(EmployeeDto employee) {
        employee.setWorkTime(employee.getWorkTime() + 1);
    }

    public void increaseRestTime(EmployeeDto employee) {
        employee.setRestTime(employee.getRestTime() + 1);
    }

    public void stopWork(EmployeeDto employee) {

        employee.setWorkTime(0);
        if (employee.getRestTime() == 0) {
            employee.setRestTime(1);
        }
        CheckoutLineDto checkoutLine = employee.getCheckoutLine();
        if (checkoutLine != null) {
            log.info(employee.fullName()
                    + " отработала смену и освободила кассу " + employee.getCheckoutLine().getLineNumber());
            checkoutLine.setEmployeeDto(null);

        }
        employee.setCheckoutLine(null);
    }

    public void startWork(EmployeeDto employee, CheckoutLineDto checkoutLine) {
        if (employee.getCheckoutLine() != null) {
            log.error(employee.fullName() + " уже работает за другой кассой");
        }
        else if (checkoutLine.getEmployeeDto() != null) {
            log.error("За кассой " + checkoutLine.getLineNumber() + " уже работает " + checkoutLine.getEmployeeDto().fullName());
        }
        else if (employee.getRestTime() < REST_TIME) {
            log.error(employee.fullName() + " еще не отдохнула");
        }
        else {
            employee.setWorkTime(1);
            employee.setRestTime(0);
            employee.setCheckoutLine(checkoutLine);
            checkoutLine.setEmployeeDto(employee);
            log.info(employee.fullName()
                    + " заступила на смену на кассу " + checkoutLine.getLineNumber());
        }
    }

}
