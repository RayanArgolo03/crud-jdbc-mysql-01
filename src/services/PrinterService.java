package services;

import java.util.Arrays;
import java.util.List;

public final class PrinterService {
    private PrinterService() {
    }

    public static <T extends Enum<T>> void printEnums(T[] enums) {
        List<T> list = Arrays.asList(enums);
        list.forEach(e -> System.out.printf("%d - %s \n", (list.indexOf(e) + 1), e));
    }
}
