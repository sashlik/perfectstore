package ru.hilariousstartups.javaskills.perfectstore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.model.ProductDto;
import ru.hilariousstartups.javaskills.perfectstore.model.RackCellDto;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.BuyStockCommand;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.PutOffRackCellCommand;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.PutOnRackCellCommand;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.SetPriceCommand;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private WorldContext worldContext;

    public ProductService(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public void handleBuyStockCommands(List<BuyStockCommand> buyStockCommands) {
        if (buyStockCommands != null) {
            buyStockCommands.forEach(command -> {
                if (command.getQuantity() != null && command.getQuantity() > 0) {
                    ProductDto product = worldContext.findProduct(command.getProductId());
                    if (product != null) {
                        product.setInStock(product.getInStock() + command.getQuantity());
                        worldContext.setStockCosts(worldContext.getStockCosts() + (command.getQuantity() * product.getStockPrice()));
                    }
                }
                else {
                    log.debug("Некорректное количество товара " + command.getQuantity());
                }
            });
            if (!buyStockCommands.isEmpty()) {
                worldContext.setStockCosts(worldContext.getStockCosts() + 5000);
            }
        }
    }

    public void handleSetPriceCommands(List<SetPriceCommand> setPriceCommands) {
        if (setPriceCommands != null) {
            setPriceCommands.forEach(command -> {
                ProductDto product = worldContext.findProduct(command.getProductId());
                if (product != null && command.getSellPrice() != null) {
                    if (command.getSellPrice() <= 0) {
                        log.debug("Цена должна быть больше нуля");
                    }
                    else {
                        product.setSellPrice(command.getSellPrice());
                    }
                }
            });
        }
    }

    public void handlePutOffRackCellCommands(List<PutOffRackCellCommand> putOffRackCellCommands) {
        if (putOffRackCellCommands != null) {
            putOffRackCellCommands.forEach(command -> {
                RackCellDto rackCell = worldContext.findRackCell(command.getRackCellId());
                if (rackCell != null && rackCell.getProduct() != null) {
                    Integer rackCellCount = rackCell.getProduct().getRackCellCount();
                    ProductDto product = rackCell.getProduct();
                    product.setRackCellCount(0);
                    product.setRackCell(null);
                    rackCell.setProduct(null);
                    rackCell.getProduct().setInStock(rackCell.getProduct().getInStock() + rackCellCount);
                }
            });
        }
    }

    public void handlePutOnRackCellCommands(List<PutOnRackCellCommand> putOnRackCellCommands) {
        if (putOnRackCellCommands != null) {
            putOnRackCellCommands.forEach(command -> {
                ProductDto product = worldContext.findProduct(command.getProductId());
                RackCellDto rackCell = worldContext.findRackCell(command.getRackCellId());

                if (product != null && rackCell != null) {
                    if (product.getRackCell() != null && product.getRackCell() != rackCell) {
                        log.debug("Продукт уже выставлен на другой полке");
                    }
                    else if (rackCell.getProduct() != null && rackCell.getProduct() != product) {
                        log.debug("На полке уже выставлен другой товар");
                    }
                    else if (product.getSellPrice() == null && command.getSellPrice() == null) {
                        log.debug("Не указана цена на товар");
                    }
                    else {
                        Integer onRack = Optional.ofNullable(product.getRackCellCount()).orElse(0);
                        Integer available = rackCell.getCapacity() - onRack;
                        Integer toPutOnRack = Math.min(available, command.getProductQuantity());
                        toPutOnRack = Math.min(toPutOnRack, product.getInStock());

                        product.setInStock(product.getInStock() - toPutOnRack); // Забрали со склада

                        // Выставляем на полку
                        product.setRackCell(rackCell);
                        rackCell.setProduct(product);
                        product.setRackCellCount(Optional.ofNullable(product.getRackCellCount()).orElse(0) + toPutOnRack);
                        if (command.getSellPrice() != null) {
                            product.setSellPrice(command.getSellPrice());
                        }
                    }

                }
                else {
                    log.debug("Не найден товар или полка");
                }
            });
        }
    }
}
