package ru.hilariousstartups.javaskills.perfectstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.config.ExternalConfig;
import ru.hilariousstartups.javaskills.perfectstore.model.ResultDto;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentTickRequest;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.CurrentWorldResponse;
import ru.hilariousstartups.javaskills.perfectstore.utils.MoneyUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class PerfectStoreService {


    private WorldContext worldContext;
    private DomainToViewMapper domainToViewMapper;
    private EmployeeService employeeService;
    private ProductService productService;
    private CustomerService customerService;
    private ExternalConfig externalConfig;

    @Autowired
    public PerfectStoreService(WorldContext worldContext,
                               DomainToViewMapper domainToViewMapper,
                               EmployeeService employeeService,
                               ProductService productService,
                               CustomerService customerService,
                               ExternalConfig externalConfig) {
        this.worldContext = worldContext;
        this.domainToViewMapper = domainToViewMapper;
        this.employeeService = employeeService;
        this.productService = productService;
        this.customerService = customerService;
        this.externalConfig = externalConfig;
    }

    public CurrentWorldResponse tick(CurrentTickRequest request) {

        if (!worldContext.isGameOver()) {

            employeeService.handleFireEmployeeCommands(request.getFireEmployeeCommands());
            employeeService.handleOffLineCommands(request.getSetOffCheckoutLineCommands());
            employeeService.handleHireEmployeeCommands(request.getHireEmployeeCommands());
            employeeService.handleOnLineCommands(request.getSetOnCheckoutLineCommands());
            employeeService.calcEmployeeWorkload(request);

            productService.handleBuyStockCommands(request.getBuyStockCommands());
            productService.handlePutOffRackCellCommands(request.getPutOffRackCellCommands());
            productService.handlePutOnRackCellCommands(request.getPutOnRackCellCommands());
            productService.handleSetPriceCommands(request.getSetPriceCommands());

            customerService.tick(); //  отработать поведение покупателей в торговом зале и на кассах

            worldContext.getCurrentTick().incrementAndGet();
        }

        if (worldContext.isGameOver()) {

            Double income = MoneyUtils.round(worldContext.getIncome());
            Double stockCosts = MoneyUtils.round(worldContext.getStockCosts());
            Double salaryCosts = MoneyUtils.round(worldContext.getSalaryCosts());

            double total = MoneyUtils.round(income - stockCosts - salaryCosts);
            String logText = "\nGame over! \nВыручка: " + income + "руб.\nРасходы на закупку товаров: " + stockCosts + " руб.\nРасходы на персонал: " + salaryCosts + "руб. \n\nИтого магазин заработал: " + total + "руб.";
            ResultDto result = new ResultDto("OK", total, logText);
            try {
                String resultStr = new ObjectMapper().writeValueAsString(result);
                Path path = Paths.get(externalConfig.getResultPath());
                Files.write(path, resultStr.getBytes());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            log.debug(logText);
        }


        return currentWorldResponse();
    }

    public CurrentWorldResponse currentWorldResponse() {
        return domainToViewMapper.currentWorldResponse();
    }



}
