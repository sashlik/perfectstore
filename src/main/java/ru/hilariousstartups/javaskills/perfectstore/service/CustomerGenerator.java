package ru.hilariousstartups.javaskills.perfectstore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hilariousstartups.javaskills.perfectstore.model.CustomerDto;
import ru.hilariousstartups.javaskills.perfectstore.model.CustomerMode;
import ru.hilariousstartups.javaskills.perfectstore.model.RackCellDto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
@Slf4j
public class CustomerGenerator {

    private WorldContext worldContext;
    private static AtomicInteger idCounter = new AtomicInteger(0);

    public CustomerGenerator(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public void generateCustomers() { // TODO
        if (worldContext.getCustomers() == null) {
            worldContext.setCustomers(new ArrayList<>());

            List<CustomerDto> customers = worldContext.getCustomers();

            IntStream.range(1, 10).forEach(i -> {
                CustomerDto customer = new CustomerDto();
                customer.setId(idCounter.incrementAndGet());
                customer.setMode(CustomerMode.in_hall);
                customer.setRackCellDtoIterator(new ArrayList<RackCellDto>(worldContext.getRackCells()).iterator()); // personal copy of rackCells with personal iterator
                customers.add(customer);
            });


        }
    }
}
