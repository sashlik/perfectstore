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
import ru.hilariousstartups.javaskills.perfectstore.model.vo.EmployeeExperience;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.PutOnRackCellCommand;
import ru.hilariousstartups.javaskills.perfectstore.service.*;
import ru.hilariousstartups.javaskills.perfectstore.utils.MoneyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class AppStartup implements ApplicationListener<ApplicationReadyEvent> {

    private WorldContext worldContext;
    private ExternalConfig externalConfig;
    private EmployeeService employeeService;
    private EmployeeGenerator employeeGenerator;
    private StockGenerator stockGenerator;
    private ProductService productService;
    private Dictionary dictionary;

    @Autowired
    public AppStartup(WorldContext worldContext,
                      ExternalConfig externalConfig,
                      EmployeeService employeeService,
                      EmployeeGenerator employeeGenerator,
                      StockGenerator stockGenerator,
                      ProductService productService,
                      Dictionary dictionary) {
        this.worldContext = worldContext;
        this.externalConfig = externalConfig;
        this.employeeService = employeeService;
        this.employeeGenerator = employeeGenerator;
        this.stockGenerator = stockGenerator;
        this.productService = productService;
        this.dictionary = dictionary;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.debug("Создаем мир..");
        worldContext.setCurrentTick(new AtomicInteger(0));
        worldContext.setTickCount(externalConfig.getGameDays() * 24 * 60); // tick = 1 minute
        worldContext.setIncome(0d);
        worldContext.setSalaryCosts(0D);
        worldContext.setStockCosts(0D);
        initCheckoutLines();
        initRackCells();

        if (externalConfig.getPregenerate()) {
            initEmployees();
            initStock();
            putOnRackCells();
        }
        else {
            initEmptyStock();
        }

        log.debug("Мир создан!");
    }

    private void initCheckoutLines() {
        Integer checkoutLines = dictionary.getStore().get(externalConfig.getStoreSize()).getCheckoutLines();

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

        Integer workingCheckoutLines = dictionary.getStore().get(externalConfig.getStoreSize()).getWorkingCheckoutLines();

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
                return employeeGenerator.generate(EmployeeExperience.junior);
            case 2:
                return employeeGenerator.generate(EmployeeExperience.middle);
            case 3:
            default:
                return employeeGenerator.generate(EmployeeExperience.senior);
        }
    }

    private void initEmptyStock() {
        worldContext.setStockCosts(0D);
        worldContext.setStock(stockGenerator.generateEmptyStock());
    }

    private void initStock() {
        List<ProductDto> stock = stockGenerator.generateStock();
        Double stockCosts = 0D;
        for (ProductDto product : stock) {
            stockCosts += product.getInStock() * product.getStockPrice();
        }

        worldContext.setStock(stock);
        worldContext.setStockCosts(stockCosts);
    }

    private void initRackCells() {
        List<RackCellDto> rackCells = stockGenerator.generateRackCells();
        worldContext.setRackCells(rackCells);

    }

    private void putOnRackCells() {
        List<ProductDto> productDtos = new ArrayList<>(worldContext.getStock()).stream().filter(p -> p.getInStock() > 0).collect(Collectors.toList());
        Collections.shuffle(productDtos);
        List<RackCellDto> shuffled = new ArrayList<>(worldContext.getRackCells());
        Collections.shuffle(shuffled);
        AtomicInteger cnt = new AtomicInteger(0);
        shuffled.stream().takeWhile(rackCell -> shuffled.indexOf(rackCell) < shuffled.size() / 2).forEach(rackCell -> { // fill only half of racks
            ProductDto product = productDtos.get(cnt.incrementAndGet());
            PutOnRackCellCommand putOnRackCellCommand = new PutOnRackCellCommand();
            putOnRackCellCommand.setRackCellId(rackCell.getId());
            putOnRackCellCommand.setProductId(product.getId());
            putOnRackCellCommand.setProductQuantity(rackCell.getCapacity());
            Double price = MoneyUtils.round(product.getStockPrice() * 1.2);
            putOnRackCellCommand.setSellPrice(price);
            productService.handlePutOnRackCellCommands(List.of(putOnRackCellCommand));
        });
    }




}
