package ru.hilariousstartups.javaskills.perfectstore.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class CustomerDto {

    private Integer id;
    private CustomerMode mode;

    private List<ProductInBasketDto> basket = new ArrayList<>();

    private Iterator<RackCellDto> rackCellDtoIterator;
    private RackCellDto currentRackCell;
    private CheckoutLineDto checkoutLine;

}
