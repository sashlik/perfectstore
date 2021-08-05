package ru.hilariousstartups.javaskills.perfectstore.service;

import ru.hilariousstartups.javaskills.perfectstore.model.EmployeeDto;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class EmployeeGenerator {

    private static AtomicInteger idCounter = new AtomicInteger(0);

    private static List<String> names = List.of("Алла", "Ирина", "Жанна", "Полина", "Анастасия", "Анна", "Светлана", "София", "Валентина", "Вера", "Надежда", "Людмила", "Екатерина", "Марина", "Мария", "Мирослава", "Дарья", "Евгения", "Нина", "Наталья", "Елена", "Елизавета");
    private static List<String> lastNames = List.of("Морозова", "Соколова", "Щукина", "Капустина", "Осипова", "Лапина", "Рыбакова", "Зайцева", "Голубева");

    private static String generateFirstName() {
        return names.get(ThreadLocalRandom.current().nextInt(names.size()));
    }

    private static String generateLastName() {
        return lastNames.get(ThreadLocalRandom.current().nextInt(lastNames.size()));
    }

    public static EmployeeDto generateJunior() {
        EmployeeDto employeeDto = preGen();
        employeeDto.setExperience(ThreadLocalRandom.current().nextInt(10, 30));
        employeeDto.setSalary(150);
        return employeeDto;
    }

    public static EmployeeDto generateMiddle() {
        EmployeeDto employeeDto = preGen();
        employeeDto.setExperience(ThreadLocalRandom.current().nextInt(30, 70));
        employeeDto.setSalary(270);
        return employeeDto;
    }

    public static EmployeeDto generateSenior() {
        EmployeeDto employeeDto = preGen();
        employeeDto.setExperience(ThreadLocalRandom.current().nextInt(70, 100));
        employeeDto.setSalary(420);
        return employeeDto;
    }

    private static EmployeeDto preGen() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(idCounter.incrementAndGet());
        employeeDto.setFirstName(generateFirstName());
        employeeDto.setLastName(generateLastName());
        return employeeDto;
    }

}
