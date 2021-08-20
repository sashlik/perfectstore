package ru.hilariousstartups.javaskills.perfectstore.model;

import lombok.Data;

@Data
public class ProductInBasketDto {

    private ProductDto product;
    private Integer productCount;
    private Double price;

}
