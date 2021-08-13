package ru.hilariousstartups.javaskills.perfectstore.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hilariousstartups.javaskills.perfectstore.config.ExternalConfig;
import ru.hilariousstartups.javaskills.perfectstore.model.ProductDto;
import ru.hilariousstartups.javaskills.perfectstore.model.ProductDict;
import ru.hilariousstartups.javaskills.perfectstore.model.RackCellDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class StockGenerator {


    private Dictionary dictionary;
    private ExternalConfig externalConfig;

    @Autowired
    public StockGenerator(Dictionary dictionary, ExternalConfig externalConfig) {
        this.dictionary = dictionary;
        this.externalConfig = externalConfig;
    }

    public List<ProductDto> generateStock() {
        List<ProductDto> stock = new ArrayList<>(dictionary.getStock().size());
        List<ProductDict> shuffled = new ArrayList<>(dictionary.getStock());
        Collections.shuffle(shuffled);

        shuffled.subList(0, shuffled.size() / 3).forEach(productDict -> { // возьмем первую треть товаров и закупим побольше
            stock.add(dictToProduct(productDict, generateRandomQuantity() * 2));
        });

        shuffled.subList(shuffled.size() / 3, (shuffled.size() * 2) / 3).forEach(productDict -> { // возьмем вторую треть товаров и закупим поменьше
            stock.add(dictToProduct(productDict,  generateRandomQuantity()));
        });

        shuffled.subList((shuffled.size() * 2) / 3, shuffled.size()).forEach(productDict -> { // возьмем третью треть товаров и не будем закупать
            stock.add(dictToProduct(productDict, 0));
        });

        return stock.stream().sorted(Comparator.comparing(ProductDto::getId)).collect(Collectors.toList());
    }

    private ProductDto dictToProduct(ProductDict productDict, Integer quantity) {
        return new ProductDto(productDict.getId(), productDict.getName(), productDict.getPrice(), quantity);
    }

    private Integer generateRandomQuantity() {
        switch (externalConfig.getStoreSize()) {
            case "small":
            default:
                return ThreadLocalRandom.current().nextInt(30, 60);
            case "medium":
                return ThreadLocalRandom.current().nextInt(120, 150);
            case "big":
                return ThreadLocalRandom.current().nextInt(200, 220);
        }
    }

    public List<RackCellDto> generateRackCells() {
        int rackCellPerVisibility;
        switch (externalConfig.getStoreSize()) {
            case "small":
            default:
                rackCellPerVisibility = 3;
                break;
            case "medium":
                rackCellPerVisibility = 5;
                break;
            case "big":
                rackCellPerVisibility = 9;
                break;
        }

        AtomicInteger idCnt = new AtomicInteger(0);
        List<RackCellDto> rackCells = new ArrayList<>();
        IntStream.range(1, 6).forEach(visibility -> {
            IntStream.range(1, rackCellPerVisibility + 1).forEach(i -> {
                RackCellDto cell = new RackCellDto();
                cell.setId(idCnt.incrementAndGet());
                cell.setVisibility(visibility);
                rackCells.add(cell);
            });
        });
        return rackCells;
    }

}
