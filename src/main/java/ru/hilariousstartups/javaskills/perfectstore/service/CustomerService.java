package ru.hilariousstartups.javaskills.perfectstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.model.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Service
@Slf4j
public class CustomerService {

    private WorldContext worldContext;

    public CustomerService(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public void tick() {
        worldContext.getCustomers().forEach(this::tick);
    }

    private void tick(CustomerDto customer) {
        if (customer.getMode() == CustomerMode.in_hall) {
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
                    customer.setMode(CustomerMode.at_checkout); // todo implement checkout
                        System.out.println("\n\n------\nПокупатель " +customer.getId() + " пошел на кассу\n------");
                        for (ProductInBasketDto pib : customer.getBasket()) {
                            System.out.println(pib.getProduct().getName() + " " + pib.getProductCount() + "шт : " + pib.getPrice());
                        }

                }
            });
        }


    }

    private void handleRackCell(CustomerDto customer) { // Look at rackCell product and either put it to basket or not
        RackCellDto currentRackCell = customer.getCurrentRackCell();
        ProductDto product = currentRackCell.getProduct(); // rack is not empty
        if (product != null) {
            boolean takeProduct = ThreadLocalRandom.current().nextBoolean(); // TODO implement

            if (takeProduct) {
                int count = ThreadLocalRandom.current().nextInt(1, 4);
                count = Math.min(count, product.getRackCellCount());
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
