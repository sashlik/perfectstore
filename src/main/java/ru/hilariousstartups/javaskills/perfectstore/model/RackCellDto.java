package ru.hilariousstartups.javaskills.perfectstore.model;

import lombok.Data;

@Data
public class RackCellDto {

    private Integer id;  // Номер полки
    private Integer visibility; // Заметность полки для покупателя (1 - самая незаметная, 5 - самая заметная)
    private Integer capacity = 10; // макс количество товара на полке
    private ProductDto product; // ссылка на продукт

}
