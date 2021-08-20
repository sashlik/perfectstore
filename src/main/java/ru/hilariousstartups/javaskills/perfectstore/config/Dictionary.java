package ru.hilariousstartups.javaskills.perfectstore.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import ru.hilariousstartups.javaskills.perfectstore.model.ProductDict;

import java.util.List;

@ConstructorBinding
@ConfigurationProperties("dict")
@AllArgsConstructor
@Getter
public class Dictionary {

    private List<ProductDict> stock;
    private List<String> firstNames;
    private List<String> lastNames;


}
