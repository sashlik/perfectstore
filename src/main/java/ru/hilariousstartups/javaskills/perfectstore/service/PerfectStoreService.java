package ru.hilariousstartups.javaskills.perfectstore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.model.CheckoutLineDto;
import ru.hilariousstartups.javaskills.perfectstore.model.EmployeeDto;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentTickRequest;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentWorldResponse;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.Employee;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.FireEmployeeCommand;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PerfectStoreService {


    private Integer totalIncome = 0;
    private WorldContext worldContext;
    private DomainToViewMapper domainToViewMapper;
    private EmployeeService employeeService;

    @Autowired
    public PerfectStoreService(WorldContext worldContext,
                               DomainToViewMapper domainToViewMapper,
                               EmployeeService employeeService) {
        this.worldContext = worldContext;
        this.domainToViewMapper = domainToViewMapper;
        this.employeeService = employeeService;
    }

    public CurrentWorldResponse tick(CurrentTickRequest request) {
        // todo реализвать проживание одного хода в магазине (предварительно применив или поставив в очередь менеджерские приказы)
        employeeService.handleFireEmployeeCommands(request.getFireEmployeeCommands());
        employeeService.handleHireEmployeeCommands(request.getHireEmployeeCommands());
        employeeService.calcEmployeeWorkload(request);

        if (!worldContext.isGameOver()) {
            worldContext.getCurrentTick().incrementAndGet();
        }
        else {
            log.info("Game over! Итого магазин заработал" + worldContext.getTotalIncome());
        }

        CurrentWorldResponse response = domainToViewMapper.currentWorldResponse();
        response.setIncome(totalIncome);


        return response;
    }



}
