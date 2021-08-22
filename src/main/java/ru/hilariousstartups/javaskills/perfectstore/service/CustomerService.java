package ru.hilariousstartups.javaskills.perfectstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.model.*;
import ru.hilariousstartups.javaskills.perfectstore.utils.MoneyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class CustomerService {

    private WorldContext worldContext;


    public CustomerService(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public void tick() {
        List<CustomerDto> leavingCustomers = new ArrayList<>();
        processHall(leavingCustomers);
       processWaitingQueue();
       processCheckout(leavingCustomers);

       worldContext.getCustomers().removeAll(leavingCustomers);
    }



    private void processHall(List<CustomerDto> leavingCustomers) {
        worldContext.getCustomers().stream()
                .filter(c -> c.getMode() == CustomerMode.in_hall)
                .forEach(c -> processHall(c, leavingCustomers));
    }

    private void processWaitingQueue() {
        worldContext.getCheckoutLines().forEach(checkoutLine -> {
            if (checkoutLine.getEmployeeDto() != null && checkoutLine.getCustomer() == null) { // employee presents and checkout is not occupied by another customer
                CustomerDto waitingCustomer = worldContext.getCheckoutQueue().poll();
                if (waitingCustomer != null) { // there was waiting customer
                    checkoutLine.setCustomer(waitingCustomer);
                    waitingCustomer.setCheckoutLine(checkoutLine);
                    waitingCustomer.setMode(CustomerMode.at_checkout);
                    calcCheckoutTime(waitingCustomer);
                    log.info(worldContext.getCurrentTick().get() + " тик: Покупатель " +waitingCustomer.getId() + " расплачивается на кассе " + checkoutLine.getLineNumber());
                }

            }
        });
    }

    private void processCheckout(List<CustomerDto> leavingCustomers) {
        List<CustomerDto> customersAtCheckout = worldContext.getCustomers().stream()
                .filter(customer -> customer.getMode() == CustomerMode.at_checkout)
                .filter(customer -> customer.getFinishCheckoutTick() == worldContext.getCurrentTick().get())
                .collect(Collectors.toList());
        customersAtCheckout.forEach(c -> leaveStore(c, leavingCustomers));
    }

    private void calcCheckoutTime(CustomerDto customer) {
        customer.setFinishCheckoutTick(worldContext.getCurrentTick().get() + 5); // todo implement
    }

    private void processHall(CustomerDto customer, List<CustomerDto> leavingCustomers) {
        int rackCountToHandle = ThreadLocalRandom.current().nextInt(2, 5); // customer can handle 2 to 5 rack cells per minute
        IntStream.range(0, rackCountToHandle)
                .takeWhile(i -> customer.getMode() == CustomerMode.in_hall)
                .forEach(i -> {
                    if (customer.getRackCellDtoIterator().hasNext()) {
                        RackCellDto rackCell = customer.getRackCellDtoIterator().next();
                        customer.setCurrentRackCell(rackCell);
                        handleRackCell(customer);
                    }
                    else {
                        if (customer.getBasket().isEmpty()) {
                           leaveStore(customer, leavingCustomers);
                        } else {
                            customer.setMode(CustomerMode.wait_checkout);
                            worldContext.getCheckoutQueue().add(customer);
                            log.info(worldContext.getCurrentTick() + " тик: Покупатель " +customer.getId() + " встал в очередь на кассу (" + customer.prettyPrintBasket() + ")");
                        }
                    }
                });
    }

    private void leaveStore(CustomerDto customer, List<CustomerDto> leavingCustomers) {
        if (customer.getCheckoutLine() != null) {
            customer.getCheckoutLine().setCustomer(null);
            Double income = MoneyUtils.round(calcIncome(customer));
            worldContext.setIncome(worldContext.getIncome() + income);
            log.info(worldContext.getCurrentTick() + " тик: Покупатель " + customer.getId() + " покинул магазин (прибыль " + income + "руб.)");
        }
        else {
            log.warn(worldContext.getCurrentTick() + " тик: Покупатель " + customer.getId() + " покинул магазин без покупок");
        }

        leavingCustomers.add(customer);
    }

    private Double calcIncome(CustomerDto customer) {
        Double income = 0d;
        for (ProductInBasketDto productInBasket : customer.getBasket()) {
            income += (productInBasket.getPrice() - productInBasket.getProduct().getStockPrice()) * productInBasket.getProductCount();
        }
        return income;
    }


    private void handleRackCell(CustomerDto customer) { // Look at rackCell product and either put it to basket or not
        RackCellDto currentRackCell = customer.getCurrentRackCell();
        ProductDto product = currentRackCell.getProduct(); // rack is not empty
        if (product != null) {
            boolean takeProduct = ThreadLocalRandom.current().nextBoolean(); // TODO implement

            if (takeProduct) {
                int count = ThreadLocalRandom.current().nextInt(1, 4);
                count = Math.min(count, product.getRackCellCount());

                if (count > 0) {
                    product.setRackCellCount(product.getRackCellCount() - count); // take from rack
                    ProductInBasketDto productInBasket = new ProductInBasketDto();
                    productInBasket.setPrice(product.getSellPrice());
                    productInBasket.setProduct(product);
                    productInBasket.setProductCount(count);
                    customer.getBasket().add(productInBasket);
                }
            }
        }

    }
}
