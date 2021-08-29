package ru.hilariousstartups.javaskills.perfectstore.model;

import lombok.Data;

@Data
public class CustomerDict {

    private Integer minCustomers;
    private Integer maxCustomers;
    private Integer entranceCapacity;

}
