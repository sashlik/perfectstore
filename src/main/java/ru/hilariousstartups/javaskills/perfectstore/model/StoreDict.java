package ru.hilariousstartups.javaskills.perfectstore.model;

import lombok.Data;

@Data
public class StoreDict {

    private Integer checkoutLines;
    private Integer workingCheckoutLines;
    private Integer rackCellPerVisibility;
    private Integer minCustomers;
    private Integer maxCustomers;
    private Integer entranceCapacity;

}
