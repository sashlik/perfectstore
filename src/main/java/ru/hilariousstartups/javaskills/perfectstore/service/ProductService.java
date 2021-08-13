package ru.hilariousstartups.javaskills.perfectstore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hilariousstartups.javaskills.perfectstore.model.ProductDto;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.BuyStockCommand;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.SetOnCheckoutLineCommand;

import java.util.List;

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
                    log.error("Некорректное количество товара " + command.getQuantity());
                }
            });
        }
    }
}
