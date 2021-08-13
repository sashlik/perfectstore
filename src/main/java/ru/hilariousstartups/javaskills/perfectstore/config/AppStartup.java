package ru.hilariousstartups.javaskills.perfectstore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.hilariousstartups.javaskills.perfectstore.model.CheckoutLineDto;
import ru.hilariousstartups.javaskills.perfectstore.model.EmployeeDto;
import ru.hilariousstartups.javaskills.perfectstore.model.ProductDto;
import ru.hilariousstartups.javaskills.perfectstore.model.RackCellDto;
import ru.hilariousstartups.javaskills.perfectstore.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@Component
public class AppStartup implements ApplicationListener<ApplicationReadyEvent> {

    private WorldContext worldContext;
    private ExternalConfig externalConfig;
    private EmployeeService employeeService;
    private EmployeeGenerator employeeGenerator;
    private StockGenerator stockGenerator;

    @Autowired
    public AppStartup(WorldContext worldContext,
                      ExternalConfig externalConfig,
                      EmployeeService employeeService,
                      EmployeeGenerator employeeGenerator,
                      StockGenerator stockGenerator) {
        this.worldContext = worldContext;
        this.externalConfig = externalConfig;
        this.employeeService = employeeService;
        this.employeeGenerator = employeeGenerator;
        this.stockGenerator = stockGenerator;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("Создаем мир..");
        worldContext.setCurrentTick(new AtomicInteger(0));
        worldContext.setTickCount(externalConfig.getGameDays() * 24 * 60); // tick = 1 minute
        worldContext.setIncome(0);
        worldContext.setSalaryCosts(0D);
        worldContext.setStockCosts(0D);
        initCheckoutLines();
        initEmployees();
        initStock();
        initRackCells();
        log.info("Мир создан!");
    }

    private void initCheckoutLines() {
        Integer checkoutLines;

        switch (externalConfig.getStoreSize()) {
            case "small":
            default:
                checkoutLines = 2;
                break;
            case "medium":
                checkoutLines = 8;
                break;
            case "big":
                checkoutLines = 16;
                break;
        }

        List<CheckoutLineDto> lines = new ArrayList<>();
        IntStream.range(0, checkoutLines).forEach((i) -> {
            lines.add(new CheckoutLineDto(i + 1));
        });
        worldContext.setCheckoutLines(lines);
    }

    private void initEmployees() {
        // Сгенерим для каджой кассы по два сотрудника. Одного сажаем за кассу, второй его сменщик, отдыхает.
        // Для маленького магазина все кассы заполняем. Для среднего и большого только часть (в ашанах все кассы не заполнены обычно).
        // Опытность кассиров генерим случайно

        Integer workingCheckoutLines;

        switch (externalConfig.getStoreSize()) {
            case "small":
            default:
                workingCheckoutLines = 2;
                break;
            case "medium":
                workingCheckoutLines = 5;
                break;
            case "big":
                workingCheckoutLines = 11;
                break;
        }

        List<EmployeeDto> employeeDtos = new ArrayList<>();
        IntStream.range(0, workingCheckoutLines).forEach((i) -> {
            EmployeeDto emp1 = generateRandomExperienceEmployee();
            EmployeeDto emp2 = generateRandomExperienceEmployee();

            employeeDtos.add(emp1);
            employeeDtos.add(emp2);
            worldContext.setEmployees(employeeDtos);

            CheckoutLineDto checkoutLine = worldContext.getCheckoutLines().get(i);

            employeeService.startWork(emp1, checkoutLine);
        });
    }

    private EmployeeDto generateRandomExperienceEmployee() {
        switch (ThreadLocalRandom.current().nextInt(1,4)) {
            case 1:
                return employeeGenerator.generateJunior();
            case 2:
                return employeeGenerator.generateMiddle();
            case 3:
            default:
                return employeeGenerator.generateSenior();
        }
    }

    private void initStock() {
        List<ProductDto> stock = stockGenerator.generateStock();
        worldContext.setStock(stock);
    }

    private void initRackCells() {
        List<RackCellDto> rackCells = stockGenerator.generateRackCells();
        worldContext.setRackCells(rackCells);
    }


}
