package ru.hilariousstartups.javaskills.perfectstore.model;

import lombok.Data;

@Data
public class ProductDto {

    private Integer id;
    private String name;
    private Double stockPrice; // Закупочная цена
    private Integer inStock;  // Количество закупленного товара на складе

    private RackCellDto rackCell; // на какой полке выложен
    private Integer rackCellCount; // количество товара на полке
    private Double sellPrice; // Цена для покупателя

    public ProductDto(Integer id, String name, Double stockPrice, Integer inStock) {
        this.id = id;
        this.name = name;
        this.stockPrice = stockPrice;
        this.inStock = inStock;
    }

}
