package ru.hilariousstartups.javaskills.perfectstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hilariousstartups.javaskills.perfectstore.config.Dictionary;
import ru.hilariousstartups.javaskills.perfectstore.model.EmployeeDto;
import ru.hilariousstartups.javaskills.perfectstore.model.vo.EmployeeExperience;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class EmployeeGenerator {

    private static AtomicInteger idCounter = new AtomicInteger(0);
    private Dictionary dictionary;

    @Autowired
    public EmployeeGenerator(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public EmployeeDto generate(EmployeeExperience experience) {
        EmployeeDto employeeDto = preGen();
        employeeDto.setExperience(ThreadLocalRandom.current().nextInt(getExpFrom(experience), getExpTo(experience)));
        employeeDto.setSalary(dictionary.getEmployee().get(experience).getSalary());
        return employeeDto;
    }

    private EmployeeDto preGen() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(idCounter.incrementAndGet());
        employeeDto.setFirstName(generateFirstName());
        employeeDto.setLastName(generateLastName());
        return employeeDto;
    }

    private String generateFirstName() {
        return dictionary.getFirstNames().get(ThreadLocalRandom.current().nextInt(dictionary.getFirstNames().size()));
    }

    private String generateLastName() {
        return dictionary.getLastNames().get(ThreadLocalRandom.current().nextInt(dictionary.getLastNames().size()));
    }

    private Integer getExpFrom(EmployeeExperience experience) {
        return getExp(experience)[0];
    }

    private Integer getExpTo(EmployeeExperience experience) {
        return getExp(experience)[1];
    }

    private Integer[] getExp(EmployeeExperience experience) {
        return Arrays.stream(dictionary.getEmployee().get(experience).getExperience().split("-")).map(Integer::valueOf).collect(Collectors.toList()).toArray(Integer[]::new);
    }

}
