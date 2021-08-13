package ru.hilariousstartups.javaskills.perfectstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hilariousstartups.javaskills.perfectstore.model.EmployeeDto;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EmployeeGenerator {

    private static AtomicInteger idCounter = new AtomicInteger(0);
    private Dictionary dictionary;

    @Autowired
    public EmployeeGenerator(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public EmployeeDto generateJunior() {
        EmployeeDto employeeDto = preGen();
        employeeDto.setExperience(ThreadLocalRandom.current().nextInt(10, 30));
        employeeDto.setSalary(150);
        return employeeDto;
    }

    public EmployeeDto generateMiddle() {
        EmployeeDto employeeDto = preGen();
        employeeDto.setExperience(ThreadLocalRandom.current().nextInt(30, 70));
        employeeDto.setSalary(270);
        return employeeDto;
    }

    public EmployeeDto generateSenior() {
        EmployeeDto employeeDto = preGen();
        employeeDto.setExperience(ThreadLocalRandom.current().nextInt(70, 100));
        employeeDto.setSalary(420);
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

}
