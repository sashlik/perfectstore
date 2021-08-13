package ru.hilariousstartups.javaskills.perfectstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class ProductDict {

    private static AtomicInteger idCounter = new AtomicInteger(0);

    private String name;
    private Double price;
    private Integer id;

    public ProductDict(String name, Double price) {
        this.id = idCounter.incrementAndGet();
        this.name = name;
        this.price = price;
    }





}
