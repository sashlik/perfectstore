package ru.hilariousstartups.javaskills.perfectstore.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import ru.hilariousstartups.javaskills.perfectstore.model.EmployeeDict;
import ru.hilariousstartups.javaskills.perfectstore.model.ProductDict;
import ru.hilariousstartups.javaskills.perfectstore.model.StoreDict;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.EmployeeExperience;

import java.util.List;
import java.util.Map;

@ConstructorBinding
@ConfigurationProperties("dict")
@AllArgsConstructor
@Getter
public class Dictionary {

    private List<ProductDict> stock;
    private List<String> firstNames;
    private List<String> lastNames;
    private Map<String, StoreDict> store;
    private Map<EmployeeExperience, EmployeeDict> employee;


}
