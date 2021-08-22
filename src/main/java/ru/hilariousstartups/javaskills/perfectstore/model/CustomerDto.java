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
    private Integer finishCheckoutTick; // Calculated time when employee will finish to serve customer (based on employee experience and customer basket size)

    public String prettyPrintBasket() {
        StringBuilder sb = new StringBuilder();
        for (ProductInBasketDto product : basket) {
            sb.append(product.getProduct().getName()).append(" ").append(product.getProductCount()).append("шт по ").append(product.getPrice()).append("руб; ");
        }
        return sb.toString();
    }

}
