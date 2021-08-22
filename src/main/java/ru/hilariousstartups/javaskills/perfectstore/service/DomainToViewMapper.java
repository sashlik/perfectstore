package ru.hilariousstartups.javaskills.perfectstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.config.Dictionary;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.*;
import ru.hilariousstartups.javaskills.perfectstore.utils.MoneyUtils;

import java.util.stream.Collectors;

@Service
public class DomainToViewMapper {

    private WorldContext worldContext;
    private Dictionary dictionary;

    @Autowired
    public DomainToViewMapper(WorldContext worldContext, Dictionary dictionary) {
        this.worldContext = worldContext;
        this.dictionary = dictionary;
    }

    public CurrentWorldResponse currentWorldResponse() {
        CurrentWorldResponse response = new CurrentWorldResponse();
        response.setTickCount(worldContext.getTickCount());
        response.setCurrentTick(worldContext.getCurrentTick().get());
        response.setGameOver(worldContext.isGameOver());
        response.setIncome(MoneyUtils.round(worldContext.getIncome()));
        response.setSalaryCosts(MoneyUtils.round(worldContext.getSalaryCosts()));
        response.setStockCosts(MoneyUtils.round(worldContext.getStockCosts()));

        response.setRecruitmentAgency(dictionary.getEmployee().entrySet().stream()
                .map(entry -> new EmployeeRecruitmentOffer(entry.getKey().name(),
                                                entry.getValue().getExperience(),
                                                entry.getValue().getSalary()))
                .collect(Collectors.toList()));

        response.setCheckoutLines(worldContext.getCheckoutLines().stream().map(checkoutLineDto -> {
            CheckoutLine checkoutLine = new CheckoutLine();
            checkoutLine.setLineNumber(checkoutLineDto.getLineNumber());
            checkoutLine.setEmployeeId(checkoutLineDto.getEmployeeDto() != null ? checkoutLineDto.getEmployeeDto().getId() : null);
            checkoutLine.setCustomerId(checkoutLineDto.getCustomer() != null ?checkoutLineDto.getCustomer().getId() : null);
            return checkoutLine;
        }).collect(Collectors.toList()));

        if (worldContext.getEmployees() != null) {
            response.setEmployees(worldContext.getEmployees().stream().map(employeeDto -> {
                Employee employee = new Employee();
                employee.setId(employeeDto.getId());
                employee.setFirstName(employeeDto.getFirstName());
                employee.setLastName(employeeDto.getLastName());
                employee.setSalary(employeeDto.getSalary());
                employee.setExperience(employeeDto.getExperience());
                return employee;
            }).collect(Collectors.toList()));
        }

        response.setStock(worldContext.getStock().stream().map(productDto -> new Product(
                productDto.getId(), productDto.getName(), productDto.getStockPrice(), productDto.getInStock(), productDto.getSellPrice()
        )).collect(Collectors.toList()));

        response.setRackCells(worldContext.getRackCells().stream().map(cellDto -> {
            RackCell cell = new RackCell();
            cell.setId(cellDto.getId());
            cell.setCapacity(cellDto.getCapacity());
            cell.setVisibility(cellDto.getVisibility());
            if (cellDto.getProduct() != null) {
                cell.setProductId(cellDto.getProduct().getId());
                cell.setProductName(cellDto.getProduct().getName());
                cell.setProductQuantity(cellDto.getProduct().getRackCellCount());
            }
            return cell;
        }).collect(Collectors.toList()));

        if (worldContext.getCustomers() != null) {
            response.setCustomers(worldContext.getCustomers().stream().map(customerDto -> {
                Customer customer = new Customer();
                customer.setId(customerDto.getId());
                customer.setMode(customerDto.getMode());
                if (customerDto.getBasket() != null) {
                    customer.setBasket(customerDto.getBasket().stream().map(pibDto -> {
                        ProductInBasket pib = new ProductInBasket();
                        pib.setId(pibDto.getProduct().getId());
                        pib.setProductName(pibDto.getProduct().getName());
                        pib.setPriсe(pibDto.getPrice());
                        pib.setProductCount(pibDto.getProductCount());
                        return pib;
                    }).collect(Collectors.toList()));
                }
                return customer;
            }).collect(Collectors.toList()));
        }

        return response;
    }

}
