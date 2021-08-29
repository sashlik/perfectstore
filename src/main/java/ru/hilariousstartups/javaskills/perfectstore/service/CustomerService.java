package ru.hilariousstartups.javaskills.perfectstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.config.Dictionary;
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
    private Dictionary dictionary;
    private CustomerGenerator customerGenerator;

    public CustomerService(WorldContext worldContext,
                           Dictionary dictionary,
                           CustomerGenerator customerGenerator) {
        this.worldContext = worldContext;
        this.dictionary = dictionary;
        this.customerGenerator = customerGenerator;
    }

    public void tick() {
        List<CustomerDto> leavingCustomers = new ArrayList<>();
        processHall(leavingCustomers);
       processWaitingQueue();
       processCheckout(leavingCustomers);
       worldContext.getCustomers().removeAll(leavingCustomers);

       newCustomersEnter();
    }


    private void newCustomersEnter() {
        Integer desiredCount = worldContext.getDesiredCustomersCount();
        int currentCount = worldContext.getCustomers().size();
        if (desiredCount == null || currentCount <= desiredCount) {
            desiredCount = generateDesired();
            worldContext.setDesiredCustomersCount(desiredCount);
        }

        if (currentCount < desiredCount) {
            int newCustomers = Math.min(desiredCount - currentCount, dictionary.getCustomer().getEntranceCapacity());
            customerGenerator.generateCustomers(newCustomers);
        }


    }

    private Integer generateDesired() {

        int minBorder, maxBorder;
        TrafficMode traffic = traffic(worldContext.getCurrentTick().get());
        switch (traffic) {
            case high:
                minBorder = (int) Math.round(dictionary.getCustomer().getMaxCustomers() / 1.2);
                maxBorder = dictionary.getCustomer().getMaxCustomers();
                break;
            case low:
                minBorder = dictionary.getCustomer().getMinCustomers();
                maxBorder = (int) Math.round(dictionary.getCustomer().getMinCustomers() * 1.2);
                break;
            case medium:
            default:
                int medCustomers = (dictionary.getCustomer().getMinCustomers() + dictionary.getCustomer().getMaxCustomers()) / 2;
                minBorder = (int) Math.round(medCustomers / 1.1);
                maxBorder = (int) Math.round(medCustomers * 1.1);
                break;
        }
        return ThreadLocalRandom.current().nextInt(minBorder, maxBorder + 1);
    }

    public  TrafficMode traffic(Integer currentTick) { // todo make private
        int currentHour = currentTick / 60 % 24;
        if (currentHour >= 9 && currentHour <=18) {
            return TrafficMode.medium;
        }
        else if (currentHour >= 22 || currentHour <= 7) {
            return TrafficMode.low;
        }
        else {
            return TrafficMode.high;
        }
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
                    log.debug(worldContext.getCurrentTick().get() + " тик: Покупатель " +waitingCustomer.getId() + " расплачивается на кассе " + checkoutLine.getLineNumber());
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
                            log.debug(worldContext.getCurrentTick() + " тик: Покупатель " +customer.getId() + " встал в очередь на кассу (" + customer.prettyPrintBasket() + ")");
                        }
                    }
                });
    }

    private void leaveStore(CustomerDto customer, List<CustomerDto> leavingCustomers) {
        if (customer.getCheckoutLine() != null) {
            customer.getCheckoutLine().setCustomer(null);
            Double income = MoneyUtils.round(calcIncome(customer));
            worldContext.setIncome(worldContext.getIncome() + income);
            log.debug(worldContext.getCurrentTick() + " тик: Покупатель " + customer.getId() + " покинул магазин (прибыль " + income + "руб.)");
        }
        else {
            log.debug(worldContext.getCurrentTick() + " тик: Покупатель " + customer.getId() + " покинул магазин без покупок");
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
