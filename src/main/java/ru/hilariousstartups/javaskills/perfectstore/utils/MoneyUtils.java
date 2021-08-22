package ru.hilariousstartups.javaskills.perfectstore.utils;

public class MoneyUtils {

    public static Double round(Double money) {
        if (money == null) {
            return null;
        }

        return ((double)Math.round(money * 100)) / 100;
    }

}
